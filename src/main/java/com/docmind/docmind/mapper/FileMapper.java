/**
 * 文件映射器接口
 * 用于文件数据的持久化操作
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.docmind.docmind.entity.File;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件映射器接口
 * 继承BaseMapper接口，提供文件数据的CRUD操作
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {
    
    // 如需添加文件相关的自定义SQL操作，请在此添加

}

