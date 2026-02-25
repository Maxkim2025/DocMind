/**
 * 登录响应数据传输对象
 * 用于返回登录成功后的信息
 * 
 * 主要功能：
 * 1. 封装登录成功后的响应数据，包括JWT令牌和用户角色
 * 2. 作为Controller层返回登录响应的载体
 * 3. 提供前端所需的认证信息
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.dto;

import lombok.Data;

/**
 * 登录响应数据传输对象
 * 用于返回登录成功后的信息
 * 
 * 此DTO类使用了以下技术：
 * 1. Lombok的@Data注解：自动生成getter、setter、toString等方法
 * 
 * 使用场景：
 * - 用户登录成功后，服务器生成JWT令牌
 * - 服务器构建LoginResponse对象，设置令牌和用户角色
 * - Controller层将LoginResponse作为响应返回给客户端
 * - 客户端存储令牌，用于后续的请求认证
 * 
 * 响应结构：
 * {
 *   "code": 200,
 *   "message": "成功",
 *   "data": {
 *     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
 *     "role": "ADMIN"
 *   }
 * }
 */
@Data
public class LoginResponse {
    /**
     * JWT令牌
     * 登录成功后生成的JWT令牌，用于后续的认证
     * 
     * 格式：Bearer <token>
     * 存储位置：客户端应该将令牌存储在localStorage或sessionStorage中
     * 使用方式：客户端在后续请求的Authorization头中携带令牌
     * 
     * 令牌有效期：由服务器配置的jwt.expiration决定
     */
    private String token;

    /**
     * 角色
     * 用户的角色，如ADMIN、USER等
     * 
     * 用途：前端根据角色控制页面显示和功能权限
     * 权限控制：后端使用角色进行方法级别的权限控制
     * 
     * 常见角色：
     * - ADMIN：管理员角色，拥有所有权限
     * - USER：普通用户角色，拥有基本操作权限
     */
    private String role;
    
    /**
     * 无参构造函数
     * 由Lombok的@Data注解自动生成
     */
    
    /**
     * 全参构造函数
     * 由Lombok的@Data注解自动生成
     */
    
    /**
     * getter和setter方法
     * 由Lombok的@Data注解自动生成
     */
    
    /**
     * toString方法
     * 由Lombok的@Data注解自动生成
     */
}

