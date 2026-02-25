/**
 * 登录请求数据传输对象
 * 用于接收登录请求的参数
 * 
 * 主要功能：
 * 1. 封装登录请求的参数，包括用户名和密码
 * 2. 作为Controller层接收登录请求的参数载体
 * 3. 提供数据绑定和验证的基础结构
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.dto;

import lombok.Data;

/**
 * 登录请求数据传输对象
 * 用于接收登录请求的参数
 * 
 * 此DTO类使用了以下技术：
 * 1. Lombok的@Data注解：自动生成getter、setter、toString等方法
 * 
 * 使用场景：
 * - 用户登录时，客户端发送包含用户名和密码的请求
 * - 服务器端使用LoginRequest接收请求参数
 * - Controller层将LoginRequest传递给Service层进行验证
 * 
 * 注意事项：
 * - 此DTO类仅用于登录请求，不包含其他业务逻辑
 * - 密码字段在传输过程中应该使用HTTPS加密
 * - 生产环境中，应该对密码进行加密存储
 */
@Data
public class LoginRequest {
    /**
     * 用户名
     * 登录所需的用户名，用户的登录标识
     * 
     * 对应前端登录表单中的用户名输入框
     * 后端使用此字段查询用户信息并进行验证
     */
    private String username;

    /**
     * 密码
     * 登录所需的密码，用户的登录凭证
     * 
     * 对应前端登录表单中的密码输入框
     * 后端使用此字段与数据库中存储的密码进行比对
     * 
     * 注意：生产环境中，密码应该使用加密存储和验证
     */
    private String password;
    
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
     * 注意：toString方法会包含密码字段，生产环境中应该重写此方法，避免密码泄露
     */
}

