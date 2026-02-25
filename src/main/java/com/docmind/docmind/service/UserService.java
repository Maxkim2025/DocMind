/**
 * 用户服务接口
 * 定义用户相关的业务逻辑方法
 * 
 * 主要功能：
 * 1. 用户管理：提供用户的CRUD操作
 * 2. 用户查询：支持条件查询和分页查询
 * 3. 用户验证：提供用户登录验证功能
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.docmind.docmind.entity.User;

/**
 * 用户服务接口
 * 继承IService接口，提供用户相关的业务逻辑方法
 * 
 * 此接口继承自MyBatis Plus的IService接口，
 * 目前使用MyBatis Plus提供的默认实现，包含了基本的CRUD操作。
 * 如需添加用户相关的自定义业务逻辑方法，请在此接口中定义。
 * 
 * IService接口提供的默认方法包括：
 * - save(T entity): 保存实体
 * - saveBatch(Collection<T> entityList): 批量保存实体
 * - updateById(T entity): 根据ID更新实体
 * - removeById(Serializable id): 根据ID删除实体
 * - getById(Serializable id): 根据ID获取实体
 * - list(): 获取所有实体
 * - page(IPage<T> page, Wrapper<T> queryWrapper): 分页查询
 * 
 * 未来扩展方向：
 * - 用户密码加密和验证
 * - 用户角色和权限管理
 * - 用户状态管理
 * - 用户信息脱敏处理
 */
public interface UserService extends IService<User> {
    // 如需添加用户相关的自定义方法，请在此定义
    // 例如：
    // User findByUsername(String username);
    // boolean validatePassword(User user, String password);
    // List<User> findUsersByRole(String role);
}

