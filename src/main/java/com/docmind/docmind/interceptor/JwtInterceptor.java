/**
 * JWT拦截器
 * 用于验证请求中的JWT令牌并设置用户认证信息
 * 
 * 主要功能：
 * 1. 拦截HTTP请求，验证请求头中的JWT令牌
 * 2. 验证令牌的有效性和格式正确性
 * 3. 从令牌中提取用户信息并查询完整的用户数据
 * 4. 将用户信息设置到请求属性中，供后续控制器使用
 * 5. 对无效令牌的请求进行拦截并返回错误信息
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.interceptor;

import com.docmind.docmind.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.docmind.docmind.entity.User;
import com.docmind.docmind.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * JWT拦截器
 * 实现HandlerInterceptor接口，用于验证请求中的JWT令牌并设置用户认证信息
 * 
 * 此拦截器使用了以下技术：
 * 1. Spring的@Component注解：将类标记为组件，使其可以被Spring容器管理
 * 2. Spring的@Autowired注解：自动注入依赖的Bean
 * 3. Spring MVC的HandlerInterceptor接口：实现请求拦截功能
 * 4. JWT令牌：用于无状态身份验证
 * 5. MyBatis Plus：用于查询用户信息
 * 
 * 配置说明：
 * 此拦截器需要在InterceptorConfig中注册，才能生效
 * 
 * 使用场景：
 * - 客户端请求需要认证的接口时，携带JWT令牌
 * - 拦截器验证令牌的有效性
 * - 验证通过后，将用户信息设置到请求中
 * - 控制器从请求中获取用户信息，用于业务处理
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    /**
     * JWT工具类
     * 用于生成和验证JWT令牌
     * 通过@Autowired注解自动注入
     */
    @Autowired
    private JwtUtils jwtUtils;
    
    /**
     * 用户服务
     * 用于查询用户信息
     * 通过@Autowired注解自动注入
     */
    @Autowired
    private UserService userService;
    
    /**
     * 请求处理前的拦截方法
     * 验证请求中的JWT令牌并设置用户认证信息
     * 
     * @param request HTTP请求对象，用于获取请求头和设置请求属性
     * @param response HTTP响应对象，用于返回错误信息
     * @param handler 处理程序对象，通常是Controller中的方法
     * @return 是否放行请求
     *         true：令牌有效，放行请求
     *         false：令牌无效，拦截请求
     * @throws Exception 处理过程中可能出现的异常
     */
    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        // 跳过登录、hello、测试和用户注册请求的拦截
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/user/login") || requestURI.equals("/hello") || requestURI.equals("/test") || requestURI.equals("/user")) {
            return true;
        }
        
        // 从请求头中获取Authorization字段
        // 客户端应该在请求头中设置Authorization: Bearer <token>
        String token = request.getHeader("Authorization");
        
        // 检查token是否存在且格式正确
        // 验证token是否以"Bearer "前缀开头
        if(token != null && token.startsWith("Bearer ")){
            // 1. Token存在且格式符合Bearer规范
            // 截取掉前缀"Bearer "，只保留真正的JWT令牌内容
            token = token.substring(7);
            
            // 验证token是否有效
            // 使用JwtUtils验证令牌的签名和过期时间
            if(jwtUtils.validateToken(token)){
                // 从token中提取用户名
                // 令牌有效，从令牌中提取纯用户名
                String username = jwtUtils.getUsernameFromToken(token);
                
                if(username != null){
                    // 根据用户名查询用户信息
                    // 使用MyBatis Plus的QueryWrapper构建查询条件
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("username", username);
                    // 调用UserService查询用户信息
                    User user = userService.getOne(queryWrapper);
                    
                    if(user != null){
                        // 将用户信息设置到请求中，以便后续的控制器能够使用
                        // 控制器可以通过request.getAttribute("user")获取用户信息
                        request.setAttribute("user", user);
                        return true;  // Token有效，放行请求
                    } else {
                        // 用户不存在
                        // 设置HTTP状态码为401（未授权）
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        // 设置响应体为JSON格式，编码UTF-8
                        response.setContentType("application/json;charset=UTF-8");
                        // 返回JSON错误信息
                        response.getWriter().write("{\"code\":401,\"message\":\"用户不存在\"}");
                        return false;  // 拦截请求
                    }
                } else {
                    // 无法从token中提取用户名
                    // 设置HTTP状态码为401（未授权）
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    // 设置响应体为JSON格式，编码UTF-8
                    response.setContentType("application/json;charset=UTF-8");
                    // 返回JSON错误信息
                    response.getWriter().write("{\"code\":401,\"message\":\"Token无效\"}");
                    return false;  // 拦截请求
                }
            }
            else {
                // 2. Token格式对，但本身无效（过期/签名错误/篡改等）
                // 设置HTTP状态码为401（未授权）
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // 设置响应体为JSON格式，编码UTF-8
                response.setContentType("application/json;charset=UTF-8");
                // 返回JSON错误信息
                response.getWriter().write("{\"code\":401,\"message\":\"Token无效\"}");
                return false;  // 拦截请求
            }
        }
        else {
            // 3. Token不存在（null） 或 格式错误（没有"Bearer "前缀）
            // 设置HTTP状态码为401（未授权）
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 设置响应体为JSON格式，编码UTF-8
            response.setContentType("application/json;charset=UTF-8");
            // 返回JSON错误信息
            response.getWriter().write("{\"code\":401,\"message\":\"未登录\"}");
            return false;  // 拦截请求
        }
    }
}
