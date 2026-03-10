/**
 * 测试控制器
 * 用于测试无需认证的接口
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.controller;

import com.docmind.docmind.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * 提供无需认证的测试接口
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    
    /**
     * 测试接口
     * 无需认证即可访问
     * 
     * @return 接口结果
     *         成功时返回包含"test"字符串的成功响应
     */
    @GetMapping("/hello")
    public Result<String> hello() {
        // 返回成功结果，包含"hello"字符串
        return Result.success("hello");
    }
}
