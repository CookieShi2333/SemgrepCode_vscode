package com.example.owasp.a09;

import java.util.logging.*;

/**
 * A09:2021 – Logging and Monitoring Failures
 * 漏洞演示：日志和监控不足
 */
public class LoggingMonitoringFailures {
    
    private static final Logger logger = Logger.getLogger(LoggingMonitoringFailures.class.getName());
    
    /**
     * 漏洞代码：日志中记录敏感信息
     */
    public static void loginVulnerable(String username, String password) {
        // 危险：记录密码到日志！
        logger.info("Login attempt - username: " + username + ", password: " + password);
        
        System.out.println("User logged in");
    }
    
    /**
     * 漏洞代码：没有记录重要的安全事件
     */
    public static void deleteAdminAccountVulnerable(int userId) {
        // 删除账户但没有任何审计日志
        System.out.println("Admin deleted");
    }
    
    /**
     * 漏洞代码：异常被捕获但未记录
     */
    public static void processPaymentVulnerable(double amount) {
        try {
            if (amount < 0) {
                throw new IllegalArgumentException("Invalid amount");
            }
            System.out.println("Payment processed: $" + amount);
        } catch (Exception e) {
            // 捕获异常但没有记录 - 攻击者可以无声地进行攻击
            System.out.println("Error occurred");
        }
    }
    
    /**
     * 漏洞代码：应用程序没有监控异常行为
     */
    public static class VulnerableMonitoring {
        private static int failedLoginAttempts = 0;
        
        public static void attemptLogin(String username) {
            failedLoginAttempts++;
            
            if (failedLoginAttempts > 100) {
                // 甚至没有警报或通知管理员
                System.out.println("Many failed attempts detected (no action taken)");
            }
        }
    }
    
    /**
     * 改进版本：安全的日志记录
     */
    public static void loginSecure(String username, String passwordHash) {
        // 不记录密码，只记录用户名和结果
        logger.info("Login attempt for user: " + username);
        
        System.out.println("User logged in");
    }
    
    /**
     * 改进版本：记录所有关键的安全事件
     */
    public static void deleteAdminAccountSecure(int userId, String performedBy) {
        // 记录详细的审计日志
        logger.warning("CRITICAL: Admin account " + userId + " deleted by " + performedBy);
        
        // 通知系统管理员
        sendSecurityAlert("Admin deletion", "Account " + userId + " was deleted");
        
        System.out.println("Admin account deleted (logged and alerted)");
    }
    
    /**
     * 改进版本：记录异常和错误详情
     */
    public static void processPaymentSecure(double amount) {
        try {
            if (amount < 0) {
                throw new IllegalArgumentException("Invalid payment amount: " + amount);
            }
            
            System.out.println("Payment processed: $" + amount);
            logger.info("Payment processed successfully: $" + amount);
            
        } catch (IllegalArgumentException e) {
            // 记录异常详情（但不记录敏感信息）
            logger.severe("Payment processing failed: " + e.getMessage());
            
            // 发送警报
            sendSecurityAlert("Payment error", "Invalid payment attempt detected");
            
            throw e;
        }
    }
    
    /**
     * 改进版本：实现异常监控和警报
     */
    public static class SecureMonitoring {
        private static int failedLoginAttempts = 0;
        private static final int ALERT_THRESHOLD = 5;
        
        public static void attemptLogin(String username, boolean success) {
            if (!success) {
                failedLoginAttempts++;
                logger.warning("Failed login attempt for user: " + username);
                
                // 当失败次数达到阈值时发送警报
                if (failedLoginAttempts > ALERT_THRESHOLD) {
                    sendSecurityAlert("Brute force attempt", 
                            "Detected " + failedLoginAttempts + " failed login attempts");
                }
            } else {
                failedLoginAttempts = 0;
                logger.info("Successful login for user: " + username);
            }
        }
        
        public static void monitorAbnormalActivity(String metric, double value, double threshold) {
            if (value > threshold) {
                logger.warning("Abnormal activity detected: " + metric + " = " + value);
                sendSecurityAlert("Anomaly detection", metric + " exceeded threshold");
            }
        }
    }
    
    /**
     * 改进版本：集中日志管理和分析
     */
    public static void setupSecureLogging() {
        try {
            // 配置日志输出到文件和 SIEM（安全信息事件管理）
            Handler fileHandler = new FileHandler("/var/log/app/security.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            
            System.out.println("Logging configured with file rotation and SIEM integration");
        } catch (Exception e) {
            logger.severe("Failed to setup logging: " + e.getMessage());
        }
    }
    
    private static void sendSecurityAlert(String alertType, String message) {
        // 实现实际的警报系统（邮件、Slack、PagerDuty 等）
        System.out.println("🚨 SECURITY ALERT: " + alertType + " - " + message);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Logging and Monitoring Failures Demo ===");
        
        System.out.println("\n=== Vulnerable Code ===");
        System.out.println("1. Logging passwords and sensitive data");
        loginVulnerable("admin", "SecurePassword123!");
        System.out.println("2. Not logging critical security events");
        System.out.println("3. Catching exceptions without logging");
        System.out.println("4. No alerting for abnormal activity");
        
        System.out.println("\n=== Secure Code ===");
        System.out.println("1. Never log passwords or personal data");
        System.out.println("2. Log all critical events with context");
        System.out.println("3. Log and handle all exceptions");
        System.out.println("4. Real-time monitoring and alerting");
        System.out.println("5. Centralized logging and SIEM integration");
    }
}
