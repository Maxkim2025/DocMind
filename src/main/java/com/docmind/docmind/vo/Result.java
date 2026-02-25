/**
 * 响应结果类
 * 用于统一API响应格式
 * 
 * 主要功能：
 * 1. 统一API响应格式，确保所有接口返回结构一致
 * 2. 提供成功和失败的响应创建方法
 * 3. 支持泛型数据类型，适应不同接口的返回数据需求
 * 
 * @param <T> 数据类型，响应数据的类型
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.vo;

/**
 * 响应结果类
 * 用于统一API响应格式
 * 
 * 此类使用了泛型设计，可以适应不同类型的响应数据
 * 所有Controller方法都应该返回此类的实例，确保响应格式一致
 * 
 * 响应格式：
 * {
 *   "code": 200,  // 响应码，200表示成功，500表示失败
 *   "message": "success",  // 响应消息，成功或失败的描述
 *   "data": {}  // 响应数据，成功时返回的数据
 * }
 * 
 * 使用场景：
 * - 接口成功时：返回Result.success(data)
 * - 接口失败时：返回Result.error(message)
 * - 前端根据code字段判断请求是否成功
 * - 前端根据message字段显示提示信息
 * - 前端根据data字段获取业务数据
 */
public class Result<T> {
    /**
     * 响应码
     * 200表示成功，500表示失败
     * 
     * 取值范围：
     * - 200：请求成功
     * - 500：请求失败
     * 
     * 前端可以根据此字段判断请求是否成功
     */
    private Integer code;
    
    /**
     * 响应消息
     * 成功或失败的描述信息
     * 
     * 取值说明：
     * - 成功时："success"
     * - 失败时：具体的错误信息
     * 
     * 前端可以根据此字段显示提示信息
     */
    private String message;
    
    /**
     * 响应数据
     * 成功时返回的数据，失败时为null
     * 
     * 类型：泛型T，可以是任何类型的数据
     * 常见类型：
     * - 单个对象：User、File等
     * - 集合：List<User>、List<File>等
     * - 简单类型：String、Integer等
     * - 分页对象：Page<User>等
     * 
     * 前端可以根据此字段获取业务数据
     */
    private T data;

    /**
     * 获取响应码
     * @return 响应码，200表示成功，500表示失败
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置响应码
     * @param code 响应码，200表示成功，500表示失败
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取响应消息
     * @return 响应消息，成功或失败的描述信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置响应消息
     * @param message 响应消息，成功或失败的描述信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取响应数据
     * @return 响应数据，成功时返回的数据，失败时为null
     */
    public T getData() {
        return data;
    }

    /**
     * 设置响应数据
     * @param data 响应数据，成功时返回的数据，失败时为null
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 创建成功响应
     * 用于接口成功时返回数据
     * 
     * @param data 响应数据，成功时返回的数据
     * @param <T> 数据类型，响应数据的类型
     * @return 成功响应，包含响应码200、消息"success"和响应数据
     *         格式：{"code": 200, "message": "success", "data": data}
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);  // 设置响应码为200，表示成功
        result.setMessage("success");  // 设置响应消息为"success"
        result.setData(data);  // 设置响应数据
        return result;
    }

    /**
     * 创建失败响应
     * 用于接口失败时返回错误信息
     * 
     * @param message 失败消息，具体的错误信息
     * @param <T> 数据类型，响应数据的类型
     * @return 失败响应，包含响应码500、错误消息和null数据
     *         格式：{"code": 500, "message": "错误信息", "data": null}
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);  // 设置响应码为500，表示失败
        result.setMessage(message);  // 设置响应消息为具体的错误信息
        // 失败时data为null，不需要设置
        return result;
    }
    
    /**
     * 无参构造函数
     * 创建一个空的Result对象
     */
    public Result() {
    }
    
    /**
     * 全参构造函数
     * 创建一个完整的Result对象
     * 
     * @param code 响应码
     * @param message 响应消息
     * @param data 响应数据
     */
    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
