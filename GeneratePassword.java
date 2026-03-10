import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 生成BCrypt加密密码的工具类
 * 用于生成用户密码
 */
public class GeneratePassword {
    public static void main(String[] args) {
        // 创建BCryptPasswordEncoder实例
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // 原始密码
        String password = "123456";
        
        // 生成加密密码
        String encryptedPassword = encoder.encode(password);
        
        // 输出结果
        System.out.println("原始密码: " + password);
        System.out.println("BCrypt加密密码: " + encryptedPassword);
        
        // 生成SQL更新语句
        System.out.println("\nSQL更新语句:");
        System.out.println("UPDATE \"user\" SET password = '" + encryptedPassword + "' WHERE username = 'user1';");
    }
}
