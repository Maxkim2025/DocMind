/**
 * 用户实体类
 * 对应数据库中的user表，存储用户的基本信息
 * 
 * 主要功能：
 * 1. 映射数据库user表结构
 * 2. 存储用户的基本信息，如用户名、密码、邮箱、电话等
 * 3. 存储用户的角色信息，用于权限控制
 * 4. 记录用户的创建和更新时间
 * 5. 提供数据验证规则
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库中的user表，存储用户的基本信息
 * 
 * 此实体类使用了以下技术：
 * 1. MyBatis Plus注解：用于数据库映射
 * 2. Lombok的@Data注解：自动生成getter、setter、toString等方法
 * 3. Jakarta Validation注解：用于数据验证
 * 
 * 表结构说明：
 * - 表名：user
 * - 主键：id（自增）
 * - 字段：username, password, email, phone, position, gender, role, create_time, update_time
 * 
 * 使用场景：
 * - 用户注册时，创建User实体并保存到数据库
 * - 用户登录时，根据用户名查询User实体并验证密码
 * - 用户管理时，查询和修改User实体
 * - 权限控制时，根据User实体的role字段判断权限
 */
@Data
@TableName("\"user\"")  // 指定数据库表名，使用引号是因为user是SQL关键字
public class User {

    /**
     * 用户ID
     * 自增主键，唯一标识用户
     * 
     * @TableId注解：指定此字段为表的主键
     * type = IdType.AUTO：指定主键生成策略为自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     * 用户的登录名，唯一
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：username
     * 
     * @NotBlank注解：验证字段不能为空
     * message = "用户名不能为空"：验证失败时的错误信息
     */
    @TableField("username")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     * 用户的登录密码
     * 注意：生产环境中应该使用加密存储
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：password
     * 
     * @NotBlank注解：验证字段不能为空
     * message = "密码不能为空"：验证失败时的错误信息
     */
    @TableField("password")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 邮箱
     * 用户的电子邮箱
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：email
     * 
     * @Email注解：验证邮箱格式是否正确
     * message = "邮箱格式不正确"：验证失败时的错误信息
     * 
     * @NotBlank注解：验证字段不能为空
     * message = "邮箱不能为空"：验证失败时的错误信息
     */
    @TableField("email")
    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 电话
     * 用户的电话号码
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：phone
     * 
     * @NotBlank注解：验证字段不能为空
     * message = "手机号不能为空"：验证失败时的错误信息
     * 
     * @Pattern注解：验证字符串是否匹配正则表达式
     * regexp = "^1[3-9]\\d{9}$"：验证手机号格式，以1开头，第二位为3-9，后面跟9位数字
     * message = "手机号格式不正确"：验证失败时的错误信息
     */
    @TableField("phone")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 职位
     * 用户的职位
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：position
     * 
     * @NotBlank注解：验证字段不能为空
     * message = "职位不能为空"：验证失败时的错误信息
     */
    @TableField("position")
    @NotBlank(message = "职位不能为空")
    private String position;

    /**
     * 性别
     * 用户的性别
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：gender
     * 
     * @NotBlank注解：验证字段不能为空
     * message = "性别不能为空"：验证失败时的错误信息
     */
    @TableField("gender")
    @NotBlank(message = "性别不能为空")
    private String gender;

    /**
     * 角色
     * 用户的角色，如ADMIN、USER等
     * 用于权限控制
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：role
     */
    @TableField("role")
    private String role;

    /**
     * 创建时间
     * 用户的创建时间，插入时自动填充
     * 
     * @TableField注解：指定此字段对应的数据库列名和自动填充策略
     * value = "create_time"：对应列名
     * fill = FieldFill.INSERT：插入时自动填充
     * 自动填充逻辑在MyMetaObjectHandler中实现
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     * 用户的最后更新时间，插入和更新时自动填充
     * 
     * @TableField注解：指定此字段对应的数据库列名和自动填充策略
     * value = "update_time"：对应列名
     * fill = FieldFill.INSERT_UPDATE：插入和更新时自动填充
     * 自动填充逻辑在MyMetaObjectHandler中实现
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

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

