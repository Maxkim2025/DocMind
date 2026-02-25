/**
 * 安全配置类
 * 用于配置Spring Security的认证和授权规则，包括：
 * 1. JWT令牌验证机制
 * 2. 请求授权规则
 * 3. 会话管理策略
 * 4. 密码编码方式
 * 
 * 该类使用了以下Spring Security注解：
 * - @Configuration: 标记为配置类
 * - @EnableWebSecurity: 启用Web安全功能
 * - @EnableMethodSecurity: 启用方法级安全注解
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.config;

import com.docmind.docmind.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置类
 * 启用Web安全和方法级安全注解，配置JWT认证机制
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * JWT认证过滤器
     * 用于验证请求中的JWT令牌并设置用户认证信息
     * 该过滤器会在每个请求到达时执行，检查Authorization头中的JWT令牌
     */
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 配置安全过滤链
     * 定义Spring Security的核心安全规则
     * 
     * @param http HttpSecurity对象，用于配置安全规则
     * @return 配置后的SecurityFilterChain实例，用于处理所有HTTP请求的安全验证
     * @throws Exception 配置过程中可能出现的异常，如配置参数错误等
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF保护，因为使用JWT令牌进行身份验证
                // JWT令牌本身已经包含了防篡改机制
                .csrf(csrf -> csrf.disable())
                
                // 设置会话管理为无状态，因为使用JWT令牌
                // 无状态会话意味着服务器不会存储用户会话信息
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // 配置请求授权规则
                .authorizeHttpRequests(auth -> auth
                        // 允许登录和hello接口无需认证
                        // 登录接口必须允许匿名访问，否则用户无法获取令牌
                        .requestMatchers("/user/login", "/hello").permitAll()
                        
                        // 其他所有请求需要认证
                        // 确保所有受保护的资源都经过身份验证
                        .anyRequest().authenticated()
                        
                        // 临时允许所有请求（用于测试）
                        // 开发环境下可以取消注释该行以方便测试
                        // .anyRequest().permitAll()
                )
                
                // 在UsernamePasswordAuthenticationFilter之前添加JWT认证过滤器
                // 这样可以确保所有请求先经过JWT验证
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        
        // 构建并返回配置后的安全过滤链
        return http.build();
    }

    /**
     * 配置密码编码器
     * 用于对用户密码进行编码和验证
     * 
     * @return 密码编码器实例
     * @deprecated 临时使用NoOpPasswordEncoder，仅用于测试
     *             生产环境应该使用BCryptPasswordEncoder等安全的密码编码方式
     *             NoOpPasswordEncoder不会对密码进行加密，仅用于开发测试
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 临时使用NoOpPasswordEncoder，用于测试
        // 生产环境应该使用BCryptPasswordEncoder
        // 例如：return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}

