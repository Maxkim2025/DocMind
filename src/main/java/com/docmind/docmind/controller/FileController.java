/**
 * 文件控制器
 * 处理文件上传、下载和查询等操作
 * 
 * 主要功能：
 * 1. 文件上传：接收客户端上传的文件并保存到系统中
 * 2. 文件查询：根据文件ID获取文件信息
 * 3. 文件下载：根据文件ID下载文件
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.controller;

import com.docmind.docmind.entity.File;
import com.docmind.docmind.entity.User;
import com.docmind.docmind.service.FileService;
import com.docmind.docmind.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 文件控制器
 * 提供文件相关的RESTful接口
 * 
 * 通过此控制器，客户端可以执行文件的上传、下载和查询操作
 * 所有接口都需要用户登录才能访问（除了特别说明的接口）
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件服务
     * 提供文件相关的业务逻辑处理
     * 包含文件上传、下载和查询的核心实现
     */
    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * 接收客户端上传的文件，保存到系统中，并返回文件信息
     * 
     * @param file 要上传的文件，通过@RequestParam注解从请求中获取
     * @param request HTTP请求对象，用于获取用户信息
     *                用户信息由JwtInterceptor在请求处理前设置到请求属性中
     * @return 上传结果，包含文件信息
     *         成功时返回包含文件信息的成功响应
     *         失败时返回错误信息
     */
    @PostMapping("/upload")
    public Result<File> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        // 检查文件是否为空
        // 如果文件为空，返回错误信息
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        
        try{
            // 从请求中获取用户信息
            // 用户信息由JwtInterceptor在请求处理前设置到请求属性中
            User user = (User) request.getAttribute("user");
            if (user == null) {
                return Result.error("用户未登录");
            }
            
            // 调用服务上传文件
            // 将文件和用户信息传递给服务层处理
            File uploadFile = fileService.uploadFile(file, user);
            
            // 返回成功结果，包含上传的文件信息
            return Result.success(uploadFile);
        }
        catch(RuntimeException e){
            // 捕获并返回异常信息
            // 服务层抛出的运行时异常会被捕获并转换为错误响应
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID获取文件信息
     * 根据文件ID查询文件的详细信息
     * 
     * @param id 文件ID，通过@PathVariable注解从URL路径中获取
     * @param request HTTP请求对象，用于获取用户信息
     *                用户信息由JwtInterceptor在请求处理前设置到请求属性中
     * @return 文件信息
     *         成功时返回包含文件信息的成功响应
     *         失败时返回错误信息
     */
    @GetMapping("/{id}")
    public Result<File> getFileByid(@PathVariable Long id, HttpServletRequest request) {
        try {
            // 从请求中获取用户信息
            // 用户信息由JwtInterceptor在请求处理前设置到请求属性中
            User user = (User) request.getAttribute("user");
            if (user == null) {
                return Result.error("用户未登录");
            }
            
            // 调用服务获取文件信息
            // 将文件ID和用户信息传递给服务层处理
            File file = fileService.getFileById(id, user);
            
            // 返回成功结果，包含文件信息
            return Result.success(file);
        }catch (RuntimeException e){
            // 捕获并返回异常信息
            // 服务层抛出的运行时异常会被捕获并转换为错误响应
            return Result.error(e.getMessage());
        }

    }

    /**
     * 文件下载
     * 根据文件ID下载文件，返回文件流给客户端
     * 
     * @param id 文件ID，通过@PathVariable注解从URL路径中获取
     * @param request HTTP请求对象，用于获取用户信息
     *                用户信息由JwtInterceptor在请求处理前设置到请求属性中
     * @return 文件下载响应，包含文件流和相关响应头
     *         成功时返回包含文件流的响应
     *         失败时返回错误信息
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long id, HttpServletRequest request) {
        try {
            // 从请求中获取用户信息
            // 用户信息由JwtInterceptor在请求处理前设置到请求属性中
            User user = (User) request.getAttribute("user");
            if (user == null) {
                // 用户未登录，返回错误响应
                return ResponseEntity.badRequest()
                        .header("Content-Type", "text/plain")
                        .body(new InputStreamResource(new ByteArrayInputStream("用户未登录".getBytes())));
            }
            
            // 1. 获取文件实体
            // 调用服务获取文件的详细信息
            File fileEntity = fileService.getFileById(id, user);
            
            // 2. 获取文件输入流
            // 调用服务获取文件的输入流，用于读取文件内容
            InputStream inputStream = fileService.getFileInputStream(id, user);
            
            // 3. 包装输入流
            // 将输入流包装为InputStreamResource，以便Spring MVC可以正确处理
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            
            // 4. 设置响应头并返回
            // 设置Content-Disposition头，指示浏览器下载文件
            // 设置Content-Type头，指定文件的MIME类型
            // 设置Content-Length头，指定文件的大小
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + fileEntity.getFileName())
                    .header("Content-Type", fileEntity.getFileType())
                    .header("Content-Length", String.valueOf(fileEntity.getFileSize()))
                    .body(inputStreamResource);
        } catch (RuntimeException e) {
            // 记录错误信息
            e.printStackTrace();
            
            // 返回错误响应
            // 将异常信息转换为错误响应，返回给客户端
            return ResponseEntity.badRequest()
                    .header("Content-Type", "text/plain")
                    .body(new InputStreamResource(new ByteArrayInputStream(e.getMessage().getBytes())));
        }
    }
}
