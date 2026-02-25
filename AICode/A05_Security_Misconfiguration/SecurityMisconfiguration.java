package com.example.owasp.a05;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * A05:2021 – Security Misconfiguration
 * 漏洞演示：不安全的配置和默认设置
 */
public class SecurityMisconfiguration {
    
    /**
     * 漏洞代码：暴露敏感的配置信息
     */
    public static void exposedConfigurationVulnerable() {
        // 将数据库凭证记录到日志中
        String dbUsername = "admin";
        String dbPassword = "admin123"; // 使用默认密码
        
        System.out.println("Connecting to database: " + dbUsername + "/" + dbPassword);
        // 这信息已经暴露！
    }
    
    /**
     * 漏洞代码：使用默认凭证
     */
    public static boolean loginWithDefaultsVulnerable(String username, String password) {
        // 检查默认凭证
        if ("admin".equals(username) && "admin".equals(password)) {
            System.out.println("Login successful with default credentials!");
            return true;
        }
        return false;
    }
    
    /**
     * 漏洞代码：在生产环境中启用调试模式
     */
    public static void debugModeVulnerable() {
        System.setProperty("debug.enabled", "true");
        System.setProperty("debug.level", "VERBOSE");
        
        // 输出堆栈跟踪和敏感信息
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[10]); // ArrayIndexOutOfBoundsException
        } catch (Exception e) {
            e.printStackTrace(); // 完整的堆栈跟踪暴露
        }
    }
    
    /**
     * 改进版本：从环境变量加载安全配置
     */
    public static void loadConfigSecure() {
        String dbUsername = System.getenv("DB_USERNAME");
        String dbPassword = System.getenv("DB_PASSWORD");
        
        if (dbUsername == null || dbPassword == null) {
            throw new IllegalArgumentException("Missing database credentials in environment variables");
        }
        
        // 不记录密码
        System.out.println("Connected to database as: " + dbUsername);
    }
    
    /**
     * 改进版本：禁用默认凭证和调试模式
     */
    public static void secureConfigurationSetup() {
        System.setProperty("debug.enabled", "false");
        System.setProperty("server.version.hidden", "true");
        
        // 强制更改默认凭证
        String adminPassword = System.getenv("ADMIN_PASSWORD");
        if (adminPassword == null) {
            throw new IllegalArgumentException("Must set ADMIN_PASSWORD environment variable");
        }
        
        System.out.println("Secure configuration loaded");
    }
    
    /**
     * 改进版本：从配置文件（不在源代码中）加载敏感信息
     */
    public static void loadFromConfigFile() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("/etc/app/secure.properties"));
            
            String dbUsername = props.getProperty("db.username");
            String dbPassword = props.getProperty("db.password");
            
            System.out.println("Loaded configuration from secure file");
        } catch (Exception e) {
            System.out.println("Error loading configuration");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Security Misconfiguration Demo ===");
        
        System.out.println("\n=== Vulnerable Configuration ===");
        exposedConfigurationVulnerable();
        loginWithDefaultsVulnerable("admin", "admin");
        
        System.out.println("\n=== Secure Configuration ===");
        System.out.println("Using environment variables and secure defaults");
        System.out.println("Debug mode disabled in production");
        System.out.println("Default credentials changed");
    }
}
