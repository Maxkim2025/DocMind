/**
 * 拦截器配置类
 * 用于配置Spring MVC的拦截器，实现请求的预处理和后处理
 * 
 * 主要功能：
 * 1. 注册自定义拦截器
 * 2. 配置拦截器的拦截路径和排除路径
 * 3. 实现WebMvcConfigurer接口，集成到Spring MVC框架中
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.config;

import com.docmind.docmind.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 * 实现WebMvcConfigurer接口，用于注册自定义拦截器到Spring MVC框架
 * 
 * 通过此配置类，将JWT拦截器集成到Spring MVC的请求处理流程中
 * 确保所有受保护的请求都经过JWT令牌验证
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    
    /**
     * JWT拦截器
     * 用于验证请求中的JWT令牌并设置用户认证信息
     * 该拦截器会在请求到达控制器之前执行，验证请求的合法性
     */
    @Autowired
    private JwtInterceptor jwtInterceptor;
    
    /**
     * 注册拦截器
     * 配置拦截器的拦截规则和排除规则
     * 
     * @param registry 拦截器注册表，用于管理所有的拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册JWT拦截器到Spring MVC框架
        registry.addInterceptor(jwtInterceptor)
                
                // 拦截所有请求
                // 使用/**模式表示拦截所有路径及其子路径
                .addPathPatterns("/**")
                
                // 排除登录和hello接口
                // 登录接口必须排除，否则用户无法获取令牌
                // hello接口用于测试，也排除在外
                .excludePathPatterns("/user/login","/hello");
    }
}
