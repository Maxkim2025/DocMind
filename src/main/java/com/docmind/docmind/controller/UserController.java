/**
 * 用户控制器
 * 处理用户相关的CRUD操作和登录功能
 * 
 * 主要功能：
 * 1. 用户管理：添加、查询、更新、删除用户
 * 2. 用户查询：支持条件查询和分页查询
 * 3. 用户登录：验证用户身份并生成JWT令牌
 * 4. 权限控制：根据用户角色限制接口访问
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.docmind.docmind.dto.LoginRequest;
import com.docmind.docmind.dto.LoginResponse;
import com.docmind.docmind.entity.User;
import com.docmind.docmind.service.UserService;
import com.docmind.docmind.util.JwtUtils;
import com.docmind.docmind.vo.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 用户控制器
 * 提供用户相关的RESTful接口
 * 
 * 通过此控制器，客户端可以执行用户的管理操作和登录功能
 * 部分接口需要特定角色才能访问
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    /**
     * 用户服务
     * 提供用户相关的业务逻辑处理
     * 包含用户的CRUD操作和登录验证等功能
     */
    @Autowired
    private UserService userService;
    
    /**
     * JWT工具类
     * 用于生成和验证JWT令牌
     * 在用户登录成功后生成令牌，用于后续的身份验证
     */
    @Autowired
    private JwtUtils jwtUtils;
    
    /**
     * 密码编码器
     * 用于对用户密码进行加密和验证
     * 在添加用户时对密码进行加密存储
     * 在登录时验证密码的正确性
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 添加用户
     * 创建新用户并保存到数据库
     * 
     * @param user 用户信息，通过@RequestBody注解从请求体中获取
     *             @Valid注解表示需要验证用户信息的合法性
     * @return 添加结果，包含新创建的用户信息
     *         成功时返回包含用户信息的成功响应
     */
    @PostMapping
    public Result<User> addUser(@RequestBody @Valid User user) {
        // 对密码进行加密处理
        // 使用BCryptPasswordEncoder对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 调用服务保存用户信息
        userService.save(user);
        // 返回成功结果，包含新创建的用户信息
        return Result.success(user);
    }

    /**
     * 根据ID获取用户信息
     * 根据用户ID查询用户的详细信息
     * 
     * @param id 用户ID，通过@PathVariable注解从URL路径中获取
     * @return 用户信息
     *         成功时返回包含用户信息的成功响应
     */
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        // 调用服务根据ID获取用户信息
        User user = userService.getById(id);
        // 返回成功结果，包含用户信息
        return Result.success(user);
    }

    /**
     * 更新用户信息
     * 更新现有用户的信息
     * 
     * @param user 用户信息，通过@RequestBody注解从请求体中获取
     *             @Valid注解表示需要验证用户信息的合法性
     * @return 更新结果，包含更新后的用户信息
     *         成功时返回包含用户信息的成功响应
     */
    @PutMapping
    public Result<User> updateUser(@RequestBody @Valid User user) {
        // 调用服务更新用户信息
        userService.updateById(user);
        // 返回成功结果，包含更新后的用户信息
        return Result.success(user);
    }

    /**
     * 删除用户
     * 根据用户ID删除用户
     * 
     * @param id 用户ID，通过@PathVariable注解从URL路径中获取
     * @return 删除结果
     *         成功时返回成功响应
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        // 调用服务根据ID删除用户
        userService.removeById(id);
        // 返回成功结果，由于是删除操作，返回null
        return Result.success(null);
    }

    /**
     * 获取所有用户
     * 查询系统中的所有用户信息
     * 
     * @return 用户列表
     *         成功时返回包含用户列表的成功响应
     */
    @GetMapping
    public Result<List<User>> getAllUser(){
        // 调用服务获取所有用户
        List<User> users = userService.list();
        // 返回成功结果，包含用户列表
        return Result.success(users);
    }

    /**
     * 条件查询用户
     * 根据指定条件查询用户信息
     * 
     * @param username 用户名，可选参数
     * @param email 邮箱，可选参数
     * @param phone 电话，可选参数
     * @param position 职位，可选参数
     * @param gender 性别，可选参数
     * @return 查询结果，包含符合条件的用户列表
     *         成功时返回包含用户列表的成功响应
     */
    @GetMapping("/query")
    public Result<List<User>> queryUser(@RequestParam(required = false) String username,
                                        @RequestParam(required = false) String email ,
                                        @RequestParam(required = false) String phone ,
                                        @RequestParam(required = false) String position ,
                                        @RequestParam(required = false) String gender) {
        // 构建查询条件
        // 使用QueryWrapper构建动态查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        
        // 如果用户名不为空，添加模糊查询条件
        if(username != null&&!username.isEmpty()){
            queryWrapper.like("username" ,username);
        }
        
        // 如果邮箱不为空，添加等值查询条件
        if(email!=null&&!email.isEmpty()){
            queryWrapper.eq("email" ,email);
        }
        
        // 如果电话不为空，添加等值查询条件
        if(phone!=null&&!phone.isEmpty()){
            queryWrapper.eq("phone" ,phone);
        }
        
        // 如果职位不为空，添加等值查询条件
        if(position!=null&&!position.isEmpty()){
            queryWrapper.eq("position" ,position);
        }
        
        // 如果性别不为空，添加等值查询条件
        if(gender!=null&&!gender.isEmpty()){
            queryWrapper.eq("gender" ,gender);
        }
        
        // 执行查询
        // 调用服务根据查询条件获取用户列表
        List<User> users = userService.list(queryWrapper);
        // 返回成功结果，包含用户列表
        return Result.success(users);
    }

    /**
     * 分页查询用户
     * 根据指定条件分页查询用户信息
     * 
     * @param pageNum 页码，默认值为1
     * @param pageSize 每页大小，默认值为15
     * @param username 用户名，可选参数
     * @param email 邮箱，可选参数
     * @param phone 电话，可选参数
     * @param position 职位，可选参数
     * @param gender 性别，可选参数
     * @return 分页查询结果，包含分页信息和用户列表
     *         成功时返回包含分页结果的成功响应
     */
    @GetMapping("/page")
    public Result<Page<User>> pageUser(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "15") Integer pageSize,
                                            @RequestParam(required = false) String username,
                                            @RequestParam(required = false) String email ,
                                            @RequestParam(required = false) String phone ,
                                            @RequestParam(required = false) String position ,
                                            @RequestParam(required = false) String gender) {
        // 构建查询条件
        // 使用QueryWrapper构建动态查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        
        // 如果用户名不为空，添加模糊查询条件
        if(username != null&&!username.isEmpty()){
            queryWrapper.like("username" ,username);
        }
        
        // 如果邮箱不为空，添加等值查询条件
        if(email!=null&&!email.isEmpty()){
            queryWrapper.eq("email" ,email);
        }
        
        // 如果电话不为空，添加等值查询条件
        if(phone!=null&&!phone.isEmpty()){
            queryWrapper.eq("phone" ,phone);
        }
        
        // 如果职位不为空，添加等值查询条件
        if(position!=null&&!position.isEmpty()){
            queryWrapper.eq("position" ,position);
        }
        
        // 如果性别不为空，添加等值查询条件
        if(gender!=null&&!gender.isEmpty()){
            queryWrapper.eq("gender" ,gender);
        }

        // 执行分页查询
        // 创建分页对象，设置页码和每页大小
        Page<User> userPage = new Page<>(pageNum, pageSize);
        // 调用服务执行分页查询
        Page<User> users = userService.page(userPage, queryWrapper);
        // 返回成功结果，包含分页查询结果
        return Result.success(users);
    }

    /**
     * 用户登录
     * 验证用户身份并生成JWT令牌
     * 
     * @param loginRequest 登录请求，通过@RequestBody注解从请求体中获取
     *                    包含用户名和密码信息
     * @return 登录结果，包含token和角色信息
     *         成功时返回包含token和角色信息的成功响应
     *         失败时返回错误信息
     */
    @PostMapping("/login")
    public Result<LoginResponse> Login(@RequestBody LoginRequest loginRequest) {
        // 检查用户名是否为空
        if(loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()){
            return Result.badRequest("用户名不能为空");
        }
        
        // 检查密码是否为空
        if(loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()){
            return Result.badRequest("密码不能为空");
        }
        
        // 根据用户名查询用户
        // 使用QueryWrapper构建查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username" ,loginRequest.getUsername());
        // 调用服务根据查询条件获取用户
        User user = userService.getOne(queryWrapper);
        
        // 检查用户是否存在
        if(user == null){
            return Result.unauthorized("用户不存在");
        }
        
        // 检查密码是否正确
        // 使用BCryptPasswordEncoder验证密码
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return Result.unauthorized("密码错误");
        }
        
        // 生成token
        // 调用JWT工具类生成令牌，使用用户名作为主题
        String token = jwtUtils.generateToken(user.getUsername() + ":" + user.getRole());
        
        // 构建登录响应
        // 创建LoginResponse对象，设置token和角色信息
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRole(user.getRole());

        // 返回成功结果，包含登录响应信息
        return Result.success(loginResponse);
    }

    /**
     * 管理员接口
     * 需要ADMIN角色才能访问
     * 
     * @return 接口结果
     *         成功时返回包含"admin"字符串的成功响应
     *         无权限时返回403错误
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/admin")
    public Result<String> admin() {
        // 返回成功结果，包含"admin"字符串
        return Result.success("admin");
    }

    /**
     * 普通用户接口
     * 需要USER角色才能访问
     * 
     * @return 接口结果
     *         成功时返回包含"user"字符串的成功响应
     *         无权限时返回403错误
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public Result<String> user() {
        // 返回成功结果，包含"user"字符串
        return Result.success("user");

    }
    
    /**
     * 测试接口
     * 无需认证即可访问
     * 
     * @return 接口结果
     *         成功时返回包含"test"字符串的成功响应
     */
    @GetMapping("/test")
    public Result<String> test() {
        // 返回成功结果，包含"test"字符串
        return Result.success("test");
    }
}
