/**
 * 全局异常处理器
 * 用于统一处理系统中的异常
 * 
 * 主要功能：
 * 1. 统一处理系统中的所有异常
 * 2. 处理参数验证异常，提取并返回验证错误信息
 * 3. 统一异常响应格式，返回标准的错误响应
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.exception;

import com.docmind.docmind.vo.Result;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 使用@RestControllerAdvice注解，用于统一处理系统中的异常
 * 
 * 此异常处理器使用了以下技术：
 * 1. Spring的@RestControllerAdvice注解：标记为全局异常处理器，处理所有Controller的异常
 * 2. Spring的@ExceptionHandler注解：指定处理的异常类型
 * 3. Java 8的Stream API：用于处理参数验证异常的错误信息
 * 
 * 工作原理：
 * 1. 当Controller中的方法抛出异常时，异常会被传递到此处
 * 2. 系统根据异常类型匹配对应的@ExceptionHandler方法
 * 3. 异常处理方法处理异常并返回标准的错误响应
 * 4. 响应会被转换为JSON格式返回给客户端
 * 
 * 错误响应格式：
 * {
 *   "code": 500,
 *   "message": "错误信息",
 *   "data": null
 * }
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有异常
     * 作为默认的异常处理方法，处理所有未被其他方法专门处理的异常
     * 
     * @param e 异常对象，包含异常信息和栈轨迹
     * @return 错误响应，包含错误信息
     *         格式：{"code": 500, "message": "错误信息", "data": null}
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        // 打印异常栈信息
        // 用于开发和调试，生产环境中应该使用日志框架记录
        e.printStackTrace();
        
        // 返回错误响应
        // 使用Result.error方法创建错误响应，设置错误信息为异常的消息
        return Result.error(e.getMessage());
    }

    /**
     * 处理参数验证异常
     * 专门处理方法参数验证失败时抛出的MethodArgumentNotValidException异常
     * 
     * @param e 参数验证异常，包含字段验证错误信息
     * @return 错误响应，包含所有字段验证错误信息
     *         格式：{"code": 500, "message": "错误信息1,错误信息2", "data": null}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleValidationException(MethodArgumentNotValidException e) {
        // 提取所有字段错误信息
        // 使用Stream API处理字段错误列表，提取每个错误的默认消息并拼接为一个字符串
        String errorMsg = e.getBindingResult()
                .getFieldErrors()  // 获取所有字段错误
                .stream()          // 转换为Stream
                .map(FieldError::getDefaultMessage)  // 提取每个错误的默认消息
                .collect(Collectors.joining(","));  // 用逗号连接所有错误信息
        
        // 返回错误响应
        // 使用Result.error方法创建错误响应，设置错误信息为拼接后的字段错误信息
        return Result.error(errorMsg);
    }
    
    /**
     * 未来扩展方向：
     * - 处理特定业务异常，如文件上传失败、权限不足等
     * - 处理HTTP状态码异常，如404、403等
     * - 处理数据库异常，如主键冲突、外键约束等
     * - 添加异常日志记录，使用日志框架替代直接打印栈轨迹
     * - 根据不同环境（开发/生产）返回不同详细程度的错误信息
     */
}
