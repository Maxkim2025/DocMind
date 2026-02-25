/**
 * MyBatis Plus配置类
 * 用于配置MyBatis Plus的插件和功能，增强MyBatis的能力
 * 
 * 主要功能：
 * 1. 配置MyBatis Plus的核心拦截器
 * 2. 注册分页插件，支持数据库分页查询
 * 3. 集成到Spring Boot应用中
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus配置类
 * 配置MyBatis Plus的核心插件，增强MyBatis的功能
 * 
 * 通过此配置类，启用MyBatis Plus的分页功能，
 * 使数据库查询支持自动分页处理
 */
@Configuration
public class MyBatisPlusConfig {
    
    /**
     * 配置MyBatis Plus拦截器
     * 创建并配置MyBatis Plus的核心拦截器，用于处理各种数据库操作的增强功能
     * 
     * @return MyBatis Plus拦截器实例，包含所有注册的插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 创建MyBatis Plus拦截器
        // 拦截器是MyBatis Plus的核心组件，用于处理SQL执行前后的增强操作
        MybatisPlusInterceptor interceptor
                = new MybatisPlusInterceptor();
        
        // 创建分页插件
        // 分页插件用于自动处理分页查询，将普通查询转换为分页查询
        PaginationInnerInterceptor paginationInnerInterceptor
                = new PaginationInnerInterceptor();
        
        // 添加分页插件到拦截器
        // 这样所有的查询操作都会经过分页插件的处理
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        
        // 返回配置好的拦截器实例
        return interceptor;
    }
}
