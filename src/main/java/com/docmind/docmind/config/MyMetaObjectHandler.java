/**
 * 元对象处理器
 * 用于自动填充实体类的创建时间和更新时间字段
 * 
 * 主要功能：
 * 1. 在插入数据时自动填充创建时间和更新时间
 * 2. 在更新数据时自动填充更新时间
 * 3. 实现MetaObjectHandler接口，集成到MyBatis Plus框架中
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 元对象处理器
 * 实现MetaObjectHandler接口，用于自动填充实体类的公共字段
 * 
 * 通过此处理器，MyBatis Plus会在执行SQL操作前自动填充
 * 实体类中的时间字段，避免手动设置这些公共字段
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    
    /**
     * 插入操作时自动填充
     * 在执行INSERT语句前，自动填充实体类的创建时间和更新时间
     * 
     * @param metaObject 元对象，包含实体类的属性信息
     *                   MyBatis Plus通过此对象访问和修改实体类的属性
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充创建时间
        // strictInsertFill方法用于严格填充，只有当字段为null时才会填充
        // 参数说明：
        // 1. metaObject: 元对象
        // 2. fieldName: 字段名，这里是"createTime"
        // 3. fieldType: 字段类型，这里是LocalDateTime.class
        // 4. fieldVal: 字段值，这里是当前时间
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        
        // 填充更新时间
        // 插入数据时，创建时间和更新时间设置为相同的值
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * 更新操作时自动填充
     * 在执行UPDATE语句前，自动填充实体类的更新时间
     * 
     * @param metaObject 元对象，包含实体类的属性信息
     *                   MyBatis Plus通过此对象访问和修改实体类的属性
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 填充更新时间
        // strictUpdateFill方法用于严格填充，只有当字段为null时才会填充
        // 参数说明：
        // 1. metaObject: 元对象
        // 2. fieldName: 字段名，这里是"updateTime"
        // 3. fieldType: 字段类型，这里是LocalDateTime.class
        // 4. fieldVal: 字段值，这里是当前时间
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}