/**
 * JWT认证过滤器
 * 用于验证请求中的JWT令牌并设置Spring Security的认证信息
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.security;

import com.docmind.docmind.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证过滤器
 * 继承OncePerRequestFilter，确保每个请求只被过滤一次
 * 用于验证请求中的JWT令牌并设置Spring Security的认证信息
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * JWT工具类
     * 用于生成和验证JWT令牌
     */
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 用户详情服务
     * 用于加载用户信息
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 执行过滤操作
     * 
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @param filterChain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 从请求头中获取Authorization字段
        String authHeader = request.getHeader("Authorization");

        // 检查token是否存在且格式正确
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 提取token
            String token = authHeader.substring(7);
            // 从token中提取用户名
            String username = jwtUtils.getUsernameFromToken(token);

            // 检查用户名是否存在且当前没有认证信息
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                // 加载用户详情
                try {
                    UserDetails userDetails = 
                            userDetailsService.loadUserByUsername(username);

                    // 验证token是否有效
                    if (jwtUtils.validateToken(token)) {
                        // 创建认证令牌
                        UsernamePasswordAuthenticationToken authToken = 
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        // 设置认证详情
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        // 设置认证信息到SecurityContextHolder
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (UsernameNotFoundException e) {
                    // 用户不存在，不设置认证信息
                }
            }
        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }
}
