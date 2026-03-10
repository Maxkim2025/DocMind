/**
 * JWT工具类
 * 用于生成、解析和验证JWT令牌
 * 
 * 主要功能：
 * 1. 生成JWT令牌：根据用户名生成包含用户信息的令牌
 * 2. 验证JWT令牌：验证令牌的有效性和完整性
 * 3. 解析JWT令牌：从令牌中提取用户信息
 * 
 * @author DocMind
 * @version 1.0
 * @since 2026-02-25
 */
package com.docmind.docmind.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT工具类
 * 提供JWT令牌的生成、解析和验证功能
 * 
 * 此工具类使用了以下技术：
 * 1. Spring的@Component注解：将类标记为组件，使其可以被Spring容器管理
 * 2. Spring的@Value注解：从配置文件中读取配置值
 * 3. jjwt库：提供JWT令牌的生成、解析和验证功能
 *
 * 配置说明：
 * - jwt.secret：JWT签名密钥，用于生成和验证令牌
 * - jwt.expiration：JWT令牌过期时间，单位为毫秒
 * 
 * 使用场景：
 * - 用户登录成功后，生成JWT令牌并返回给客户端
 * - 客户端请求需要认证的接口时，携带JWT令牌
 * - 服务器收到请求后，验证JWT令牌的有效性
 * - 服务器从JWT令牌中提取用户信息，用于权限验证和业务处理
 */
@Component
public class JwtUtils {
    
    /**
     * JWT密钥
     * 从配置文件中读取，用于生成和验证JWT令牌的签名
     * 
     * 注意：生产环境中，此密钥应该保持机密，并且应该定期更换
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * JWT过期时间
     * 从配置文件中读取，单位为毫秒
     * 用于设置JWT令牌的过期时间
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成token
     * 根据主题（通常为用户名）生成JWT令牌
     * 
     * @param subject 主题，通常为用户名，用于标识用户身份
     * @return JWT令牌字符串，包含用户信息和签名
     *         格式：header.payload.signature
     */
    public String generateToken(String subject) {
        // 获取当前时间
        Date now = new Date();
        // 计算过期时间
        Date expirationDate = new Date(now.getTime() + expiration);
        
        // 使用Jwts.builder()构建JWT令牌
        return Jwts.builder()
                .setSubject(subject)  // 设置主题，通常为用户名:角色
                .claim("authorities", subject.split(":")[1])
                .setIssuedAt(now)  // 设置签发时间
                .setExpiration(expirationDate)  // 设置过期时间
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)  // 设置签名
                // 签名算法：HS256 (HMAC with SHA-256)
                // 密钥：使用secret字符串的字节数组生成
                .compact();  // 生成并返回JWT令牌

    }

    /**
     * 验证token
     * 验证JWT令牌的有效性和完整性
     * 
     * @param token JWT令牌字符串，格式：header.payload.signature
     * @return 是否有效
     *         true：令牌有效
     *         false：令牌无效（过期、签名错误、格式错误等）
     */
    public Boolean validateToken(String token) {
        try{
            // 解析并验证JWT令牌
            // 设置签名密钥，然后解析令牌
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parseClaimsJws(token);
            // 解析成功，令牌有效
            return true;
        }catch(Exception e){
            // 解析失败，token无效
            // 可能的异常：过期异常、签名错误异常、格式错误异常等
            return false;
        }
    }

    /**
     * 解析token
     * 从JWT令牌中提取主题（通常为用户名:角色）
     * 
     * @param token JWT令牌字符串，格式：header.payload.signature
     * @return 主题（用户名:角色）
     *         成功时返回主题字符串
     *         失败时返回null
     */
    public String parseToken(String token) {
        try{
            // 解析JWT令牌并获取主题
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)  // 解析令牌
                    .getBody()  // 获取令牌的载荷部分
                    .getSubject();  // 获取主题（用户名:角色）

        }
        catch(Exception e){
            // 解析失败，返回null
            // 可能的异常：过期异常、签名错误异常、格式错误异常等
            return null;
        }
    }
    
    /**
     * 从token中提取用户名
     * 
     * @param token JWT令牌字符串
     * @return 用户名
     *         成功时返回用户名字符串
     *         失败时返回null
     */
    public String getUsernameFromToken(String token) {
        try{
            String subject = parseToken(token);
            if (subject != null) {
                return subject.split(":")[0];
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
    
    /**
     * 从token中提取角色
     * 
     * @param token JWT令牌字符串
     * @return 角色
     *         成功时返回角色字符串
     *         失败时返回null
     */
    public String getRoleFromToken(String token) {
        try{
            String subject = parseToken(token);
            if (subject != null && subject.contains(":")) {
                return subject.split(":")[1];
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
}
