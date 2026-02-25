/**
 * 文件服务接口
 * 定义文件相关的业务逻辑方法
 * 
 * 主要功能：
 * 1. 文件上传：接收客户端上传的文件并保存到系统
 * 2. 文件查询：根据文件ID获取文件信息
 * 3. 文件下载：根据文件ID获取文件内容
 * 4. 文件输入流获取：获取文件的输入流用于读取
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.docmind.docmind.entity.File;
import com.docmind.docmind.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件服务接口
 * 继承IService接口，提供文件相关的业务逻辑方法
 * 
 * 此接口定义了文件操作的核心业务逻辑，
 * 包括文件上传、查询、下载和输入流获取等功能
 */
public interface FileService extends IService<File> {
    /**
     * 上传文件
     * 接收客户端上传的文件，保存到系统中，并返回文件信息
     * 
     * @param file 上传的文件，通过MultipartFile对象接收
     * @param user 当前用户，用于关联文件所有权
     * @return 文件实体，包含上传后的文件信息
     * @throws RuntimeException 文件上传失败时抛出
     *                          可能的异常原因：文件为空、文件大小超出限制、文件类型不允许、存储失败等
     */
    File uploadFile(MultipartFile file, User user);
    
    /**
     * 根据ID获取文件
     * 根据文件ID查询文件的详细信息
     * 
     * @param id 文件ID，唯一标识文件的主键
     * @param user 当前用户，用于验证文件访问权限
     * @return 文件实体，包含文件的详细信息
     * @throws RuntimeException 文件不存在或无权限访问时抛出
     *                          可能的异常原因：文件不存在、用户无权限访问该文件等
     */
    File getFileById(Long id, User user);
    
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
    InputStream downloadFile(Long id, User user);
    
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
    InputStream getFileInputStream(Long id, User user);
}
