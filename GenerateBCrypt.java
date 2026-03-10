import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 生成BCrypt加密密码的工具类
 * 用于生成管理员用户的密码
 */
public class GenerateBCrypt {
    public static void main(String[] args) {
        // 创建BCryptPasswordEncoder实例
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // 原始密码
        String password = "admin123";
        
        // 生成加密密码
        String encryptedPassword = encoder.encode(password);
        
        // 输出结果
        System.out.println("原始密码: " + password);
        System.out.println("BCrypt加密密码: " + encryptedPassword);
        
        // 生成SQL更新语句
        System.out.println("\nSQL更新语句:");
        System.out.println("UPDATE \"user\" SET password = '" + encryptedPassword + "' WHERE username = 'admin';");
    }
}
