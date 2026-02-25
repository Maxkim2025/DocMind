/**
 * 用户映射器接口
 * 用于用户数据的持久化操作
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.docmind.docmind.entity.User;

/**
 * 用户映射器接口
 * 继承BaseMapper接口，提供用户数据的CRUD操作
 */
public interface UserMapper extends BaseMapper<User> {
    
    // 如需添加用户相关的自定义SQL操作，请在此添加

}

