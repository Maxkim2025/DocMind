/**
 * DocMind应用主入口类
 * Spring Boot应用的启动类
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DocMind应用主入口类
 * 标记为Spring Boot应用，并指定Mapper扫描路径
 */
@SpringBootApplication
@MapperScan("com.docmind.docmind.mapper")
public class DocMindApplication {

    /**
     * 主方法
     * 应用程序的入口点
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(DocMindApplication.class, args);
    }

}
