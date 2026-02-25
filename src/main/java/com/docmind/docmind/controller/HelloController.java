/**
 * 测试控制器
 * 用于测试系统是否正常运行
 * 
 * 主要功能：
 * 1. 提供简单的测试接口，验证系统是否正常启动
 * 2. 作为系统健康检查的端点
 * 3. 验证Spring MVC是否正常工作
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.controller;

import com.docmind.docmind.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * 提供简单的测试接口
 * 
 * 此控制器使用了以下技术：
 * 1. Spring的@RestController注解：标记为REST风格的控制器
 * 2. Spring的@GetMapping注解：映射GET请求到方法
 * 3. Result类：统一响应格式
 * 
 * 使用场景：
 * - 系统启动后，测试系统是否正常运行
 * - 部署后，验证服务是否可用
 * - 开发过程中，测试接口调用是否正常
 * 
 * 访问路径：
 * - GET /hello：返回简单的测试响应
 */
@RestController
public class HelloController {
    
    /**
     * 测试接口
     * 提供简单的GET请求测试端点
     * 
     * @return 测试结果
     *         成功时返回包含"hello"字符串的成功响应
     *         格式：{"code": 200, "message": "成功", "data": "hello"}
     */
    @GetMapping("/hello")
    public Result<String> hello(){
        // 返回成功响应，包含"hello"字符串
        // 使用Result.success方法创建成功响应
        return Result.success("hello");
    }
}

