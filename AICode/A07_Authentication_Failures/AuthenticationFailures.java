package com.example.owasp.a07;

import java.util.HashMap;
import java.util.Map;

/**
 * A07:2021 – Identification and Authentication Failures
 * 漏洞演示：身份验证和会话管理缺陷
 */
public class AuthenticationFailures {
    
    private static Map<String, String> sessions = new HashMap<>();
    
    /**
     * 漏洞代码：没有会话超时的会话管理
     */
    public static String createSessionVulnerable(String username) {
        // 生成不安全的会话 ID（可预测）
        String sessionId = username + System.currentTimeMillis();
        sessions.put(sessionId, username);
        
        System.out.println("Session created: " + sessionId);
        return sessionId;
    }
    
    /**
     * 漏洞代码：弱密码策略
     */
    public static boolean validatePasswordVulnerable(String password) {
        // 接受任何密码，甚至很短的
        return password != null && password.length() > 0;
    }
    
    /**
     * 漏洞代码：缺少多因素认证，没有防暴力破解
     */
    public static boolean loginVulnerable(String username, String password) {
        // 直接检查，没有速率限制或锁定机制
        return "admin".equals(username) && "password".equals(password);
    }
    
    /**
     * 漏洞代码：会话 ID 可被预测
     */
    public static class VulnerableSessionManager {
        private static int sessionCounter = 0;
        
        public static String generateSessionId() {
            return "SESSION_" + (sessionCounter++);
        }
    }
    
    /**
     * 改进版本：强密码策略
     */
    public static boolean validatePasswordSecure(String password) {
        // 密码至少 12 个字符，包含大小写字母、数字和特殊字符
        return password != null &&
                password.length() >= 12 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*].*");
    }
    
    /**
     * 改进版本：安全的会话管理
     */
    public static class SecureSessionManager {
        private static Map<String, SessionInfo> sessions = new HashMap<>();
        
        public static String createSession(String username) {
            // 使用加密随机数生成器
            String sessionId = java.util.UUID.randomUUID().toString();
            
            SessionInfo info = new SessionInfo();
            info.username = username;
            info.createdAt = System.currentTimeMillis();
            info.expiresAt = System.currentTimeMillis() + (30 * 60 * 1000); // 30分钟超时
            
            sessions.put(sessionId, info);
            return sessionId;
        }
        
        public static boolean validateSession(String sessionId) {
            SessionInfo info = sessions.get(sessionId);
            
            if (info == null) {
                return false;
            }
            
            // 检查超时
            if (System.currentTimeMillis() > info.expiresAt) {
                sessions.remove(sessionId);
                return false;
            }
            
            return true;
        }
        
        public static void invalidateSession(String sessionId) {
            sessions.remove(sessionId);
        }
    }
    
    /**
     * 改进版本：实现速率限制和账户锁定
     */
    public static class SecureAuthenticationManager {
        private static Map<String, Integer> failedAttempts = new HashMap<>();
        
        public static boolean login(String username, String password) throws Exception {
            // 检查账户是否被锁定
            int attempts = failedAttempts.getOrDefault(username, 0);
            if (attempts >= 5) {
                throw new IllegalArgumentException("Account locked due to too many failed attempts");
            }
            
            // 验证凭证
            if ("admin".equals(username) && validatePasswordSecure(password)) {
                failedAttempts.remove(username);
                System.out.println("Login successful");
                return true;
            } else {
                // 增加失败次数
                failedAttempts.put(username, attempts + 1);
                
                // 延迟响应以防暴力破解
                Thread.sleep(1000 * (attempts + 1));
                throw new IllegalArgumentException("Invalid credentials");
            }
        }
    }
    
    static class SessionInfo {
        String username;
        long createdAt;
        long expiresAt;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Authentication Failures Demo ===");
        
        System.out.println("\n=== Vulnerable Code ===");
        System.out.println("1. Weak password: 'pass'");
        System.out.println("   Valid: " + validatePasswordVulnerable("pass"));
        
        System.out.println("2. Predictable session ID: " + VulnerableSessionManager.generateSessionId());
        
        System.out.println("\n=== Secure Code ===");
        System.out.println("1. Strong password required (12+ chars with mixed case, numbers, symbols)");
        System.out.println("2. Random UUID-based session IDs");
        System.out.println("3. Session timeout: 30 minutes");
        System.out.println("4. Account lockout after 5 failed attempts");
    }
}
