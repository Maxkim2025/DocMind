/**
 * 文件实体类
 * 对应数据库中的file表，存储文件的基本信息
 * 
 * 主要功能：
 * 1. 映射数据库file表结构
 * 2. 存储文件的基本信息，如文件名、大小、路径、类型等
 * 3. 关联文件所属的用户
 * 4. 记录文件的创建和更新时间
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件实体类
 * 对应数据库中的file表，存储文件的基本信息
 * 
 * 此实体类使用了MyBatis Plus的注解进行数据库映射，
 * 使用了Lombok的@Data注解自动生成getter、setter、toString等方法。
 * 
 * 表结构说明：
 * - 表名：file
 * - 主键：id（自增）
 * - 字段：file_name, file_size, file_path, file_type, user_id, create_time, update_time
 * 
 * 使用场景：
 * - 文件上传时，创建File实体并保存到数据库
 * - 文件查询时，从数据库加载File实体
 * - 文件下载时，根据File实体的信息获取文件内容
 */
@Data
@TableName("file")  // 指定数据库表名
public class File {
    /**
     * 文件ID
     * 自增主键，唯一标识文件
     * 
     * @TableId注解：指定此字段为表的主键
     * type = IdType.AUTO：指定主键生成策略为自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件名
     * 原始文件名，用户上传时的文件名
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：file_name
     * 
     * @NotBlank注解：验证字段不能为空
     * message = "文件名不能为空"：验证失败时的错误信息
     */
    @TableField("file_name")
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    /**
     * 文件大小
     * 文件的字节大小，以字节为单位
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：file_size
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件路径
     * 文件在服务器上的存储路径，完整路径
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：file_path
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件类型
     * 文件的MIME类型，如image/jpeg、application/pdf等
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：file_type
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 用户ID
     * 文件所属用户的ID，外键关联user表的id字段
     * 
     * @TableField注解：指定此字段对应的数据库列名
     * 对应列名：user_id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 创建时间
     * 文件的创建时间，插入时自动填充
     * 
     * @TableField注解：指定此字段对应的数据库列名和自动填充策略
     * value = "create_time"：对应列名
     * fill = FieldFill.INSERT：插入时自动填充
     * 自动填充逻辑在MyMetaObjectHandler中实现
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     * 文件的最后更新时间，插入和更新时自动填充
     * 
     * @TableField注解：指定此字段对应的数据库列名和自动填充策略
     * value = "update_time"：对应列名
     * fill = FieldFill.INSERT_UPDATE：插入和更新时自动填充
     * 自动填充逻辑在MyMetaObjectHandler中实现
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
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

