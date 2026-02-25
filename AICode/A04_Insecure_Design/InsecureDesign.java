package com.example.owasp.a04;

/**
 * A04:2021 – Insecure Design
 * 漏洞演示：设计缺陷导致的安全问题
 */
public class InsecureDesign {
    
    /**
     * 漏洞代码：密码重置功能没有足够的验证
     * 攻击者可以通过猜测或可预测的安全问题来重置他人密码
     */
    public static boolean resetPasswordVulnerable(String username, String securityAnswer) {
        // 非常简单的验证，可被破解
        // 假设安全问题"你的宠物名字是？"，答案是"Fluffy"
        
        if (securityAnswer.equalsIgnoreCase("Fluffy")) {
            System.out.println("Password reset successful");
            // 发送重置链接
            return true;
        }
        return false;
    }
    
    /**
     * 漏洞代码：缺少速率限制的登录
     */
    public static boolean loginVulnerable(String username, String password) {
        // 没有限制登录尝试次数，容易被暴力破解
        return checkCredentials(username, password);
    }
    
    /**
     * 改进版本：实现速率限制和账户锁定
     */
    public static boolean loginSecure(String username, String password) {
        return checkCredentialsWithRateLimit(username, password);
    }
    
    /**
     * 改进版本：使用多因素验证（MFA）
     * 必须通过电子邮件或短信确认
     */
    public static boolean resetPasswordSecure(String username, String securityAnswer, String mfaToken) {
        if (!securityAnswer.equals("correct_answer")) {
            return false;
        }
        
        // 验证 MFA 令牌
        if (!validateMFAToken(username, mfaToken)) {
            return false;
        }
        
        System.out.println("Password reset successful with MFA verification");
        return true;
    }
    
    private static boolean checkCredentials(String username, String password) {
        // 简化的凭证检查
        return "admin".equals(username) && "password123".equals(password);
    }
    
    private static boolean checkCredentialsWithRateLimit(String username, String password) {
        // 实现速率限制逻辑
        if (checkFailedAttempts(username) > 5) {
            throw new IllegalArgumentException("Too many failed attempts. Account locked.");
        }
        return checkCredentials(username, password);
    }
    
    private static int checkFailedAttempts(String username) {
        // 模拟检查失败次数
        return 0;
    }
    
    private static boolean validateMFAToken(String username, String token) {
        // 验证 MFA 令牌的逻辑
        return token != null && token.length() == 6;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Insecure Design Demo ===");
        System.out.println("Vulnerable: Simple security questions without MFA");
        resetPasswordVulnerable("user", "fluffy");
        
        System.out.println("\n=== Secure Design ===");
        System.out.println("Implemented: Rate limiting, MFA, and security hardening");
    }
}
