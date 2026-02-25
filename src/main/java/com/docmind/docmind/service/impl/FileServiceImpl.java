/**
 * 文件服务实现类
 * 实现文件相关的业务逻辑
 * 
 * 主要功能：
 * 1. 文件上传：接收客户端上传的文件，验证文件合法性，保存到磁盘并记录文件信息
 * 2. 文件查询：根据文件ID查询文件信息，验证用户权限
 * 3. 文件下载：根据文件ID获取文件内容，返回文件输入流
 * 4. 文件输入流获取：获取文件的输入流用于读取
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.docmind.docmind.entity.File;
import com.docmind.docmind.entity.User;
import com.docmind.docmind.mapper.FileMapper;
import com.docmind.docmind.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

/**
 * 文件服务实现类
 * 实现FileService接口，提供文件相关的业务逻辑处理
 * 
 * 此实现类继承自MyBatis Plus的ServiceImpl，
 * 实现了FileService接口中定义的所有方法，
 * 包含了文件上传、查询、下载和输入流获取的具体实现。
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,File> implements FileService {
    
    /**
     * 文件映射器
     * 用于文件数据的持久化操作
     * 通过构造函数注入
     */
    private final FileMapper fileMapper;
    
    /**
     * 文件上传路径
     * 从配置文件中读取，用于指定文件保存的目录
     */
    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 允许上传的文件类型
     * 可根据业务需求修改
     * 包含图片、PDF、Word文档和文本文件类型
     */
    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList(
            "image/jpeg",      // JPEG图片
            "image/png",       // PNG图片
            "image/gif",       // GIF图片
            "application/pdf", // PDF文档
            "application/msword", // Word文档（.doc）
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // Word文档（.docx）
            "text/plain");     // 文本文件

    /**
     * 构造函数
     * 通过构造函数注入FileMapper
     * 
     * @param fileMapper 文件映射器，用于文件数据的持久化操作
     */
    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    /**
     * 上传文件
     * 接收客户端上传的文件，验证文件合法性，保存到磁盘并记录文件信息
     * 
     * @param file 上传的文件，通过MultipartFile对象接收
     * @param user 当前用户，用于关联文件所有权
     * @return 文件实体，包含上传后的文件信息
     * @throws RuntimeException 文件上传失败时抛出
     *                          可能的异常原因：文件为空、文件大小超出限制、文件类型不允许、存储失败等
     */
    @Override
    public File uploadFile(MultipartFile file, User user) {
        try{
            // 检查文件是否为空
            // 如果文件为空，抛出异常
            if (file.isEmpty()){
                throw  new RuntimeException("文件不能为空");
            }

            // 检查文件大小
            // 限制文件大小为100MB
            if(file.getSize()>100*1024*1024){
                throw new RuntimeException("文件大小不能超过100mb");
            }

            // 检查文件类型
            // 验证文件类型是否在允许的列表中
            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_FILE_TYPES.contains(contentType)) {
                throw new RuntimeException("不支持的文件类型，允许的类型：图片、PDF、Word文档、文本文件");
            }

            // 确保上传目录存在
            // 如果上传目录不存在，创建目录
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            // 生成唯一文件名
            // 使用UUID生成唯一文件名，避免文件名冲突
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + fileExtension;

            // 保存文件到磁盘
            // 将文件内容写入到指定路径
            Path filePath = uploadDir.resolve(fileName);
            Files.write(filePath, file.getBytes());

            // 构建文件实体
            // 创建File对象，设置文件相关信息
            File fileEntity = new File();
            fileEntity.setFileName(originalFilename);  // 保留原始文件名
            fileEntity.setUserId(user.getId());         // 关联用户ID
            fileEntity.setFilePath(filePath.toString()); // 保存文件路径
            fileEntity.setFileSize(file.getSize());     // 保存文件大小
            fileEntity.setFileType(file.getContentType()); // 保存文件类型

            // 保存文件信息到数据库
            // 使用MyBatis Plus的saveOrUpdate方法保存文件信息
            this.saveOrUpdate(fileEntity);
            
            // 返回文件实体
            return fileEntity;

        } catch (IOException e) {
            // 捕获IO异常，转换为RuntimeException并抛出
            throw new RuntimeException("文件上传失败",e);
        }
    }

    /**
     * 根据ID获取文件
     * 根据文件ID查询文件信息，验证用户权限和文件状态
     * 
     * @param id 文件ID，唯一标识文件的主键
     * @param user 当前用户，用于验证文件访问权限
     * @return 文件实体，包含文件的详细信息
     * @throws RuntimeException 文件不存在或无权限访问时抛出
     *                          可能的异常原因：文件不存在、用户无权限访问该文件、文件路径无效、文件已被删除等
     */
    @Override
    public File getFileById(Long id, User user) {
        // 根据ID查询文件
        // 使用MyBatis Plus的getById方法查询文件
        File fileEntity = this.getById(id);

        // 检查文件是否存在
        if(fileEntity == null){
            throw new RuntimeException("文件不存在");
        }

        // 检查用户是否有权限访问该文件
        // 只有文件所有者才能访问该文件
        if (!fileEntity.getUserId().equals(user.getId())) {
            throw new RuntimeException("无权限访问该文件");
        }
        
        // 检查文件路径是否有效
        // 验证文件路径是否在上传目录内，防止路径遍历攻击
        Path filePath = Paths.get(fileEntity.getFilePath()).normalize();
        Path uploadBasePath = Paths.get(uploadPath).normalize();
        if (!filePath.startsWith(uploadBasePath)) {
            throw new RuntimeException("无效的文件路径");
        }
        
        // 检查文件是否存在于磁盘
        // 验证文件是否实际存在于指定路径
        if (!Files.exists(filePath)) {
            throw new RuntimeException("文件已经被删除或移动");
        }
        
        // 返回文件实体
        return fileEntity;
    }

    /**
     * 下载文件
     * 根据文件ID获取文件内容，返回文件输入流
     * 
     * @param id 文件ID，唯一标识文件的主键
     * @param user 当前用户，用于验证文件访问权限
     * @return 文件输入流，用于读取文件内容
     * @throws RuntimeException 文件不存在、无权限访问或读取失败时抛出
     *                          可能的异常原因：文件不存在、用户无权限访问该文件、文件读取失败等
     */
    @Override
    public InputStream downloadFile(Long id, User user) {
        // 复用已有的getFileInputStream方法，返回文件输入流
        // 避免代码重复
        return this.getFileInputStream(id, user);
    }
    
    /**
     * 获取文件输入流
     * 根据文件ID获取文件的输入流，用于读取文件内容
     * 
     * @param id 文件ID，唯一标识文件的主键
     * @param user 当前用户，用于验证文件访问权限
     * @return 文件输入流，用于读取文件内容
     * @throws RuntimeException 文件不存在、无权限访问或读取失败时抛出
     *                          可能的异常原因：文件不存在、用户无权限访问该文件、文件读取失败等
     */
    @Override
    public InputStream getFileInputStream(Long id, User user) {
        try {
            // 复用已有的getFileById方法，包含完整的验证逻辑
            // 验证文件是否存在、用户是否有权限访问、文件路径是否有效、文件是否存在于磁盘
            File fileEntity = this.getFileById(id, user);
            
            // 验证通过后，创建并返回文件输入流
            // 使用Files.newInputStream创建文件输入流
            Path filePath = Paths.get(fileEntity.getFilePath());
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            // 捕获IO异常，转换为RuntimeException并抛出
            throw new RuntimeException("文件读取失败", e);
        }
    }
}
