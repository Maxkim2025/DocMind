import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 验证密码工具类
 * 用于验证BCrypt加密密码是否正确
 */
public class VerifyPassword {
    public static void main(String[] args) {
        // 创建BCryptPasswordEncoder实例
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // 原始密码
        String rawPassword = "123456";
        
        // 我们生成的BCrypt加密密码
        String encodedPassword = "$2a$10$q940AebshPj9Rx0.Nz8qqe.kFLunZSlT7O95bSAkIH6Hsjh4r0NwO";
        
        // 验证密码
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        
        // 输出结果
        System.out.println("原始密码: " + rawPassword);
        System.out.println("加密密码: " + encodedPassword);
        System.out.println("密码是否匹配: " + matches);
        
        // 生成新的加密密码用于测试
        String newEncodedPassword = encoder.encode(rawPassword);
        System.out.println("\n新生成的加密密码: " + newEncodedPassword);
        System.out.println("新密码是否匹配: " + encoder.matches(rawPassword, newEncodedPassword));
    }
}