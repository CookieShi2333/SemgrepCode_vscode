package com.example.owasp.a02;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * A02:2021 – Cryptographic Failures
 * 漏洞演示：使用过时或弱密码算法，硬编码密钥
 */
public class CryptographicFailures {
    
    // 漏洞：硬编码密钥
    private static final String HARDCODED_KEY = "1234567890123456";
    
    /**
     * 漏洞代码：使用弱的加密算法和硬编码的密钥
     */
    public static String encryptDataVulnerable(String plaintext) throws Exception {
        byte[] decodedKey = HARDCODED_KEY.getBytes();
        SecretKeySpec key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    /**
     * 漏洞：直接存储明文密码
     */
    public static String hashPasswordVulnerable(String password) {
        // 直接返回明文或简单的 Base64，没有使用 salt
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
    
    /**
     * 改进版本：使用现代密码库和强算法
     */
    public static String hashPasswordSecure(String password) {
        // 应使用 bcrypt, scrypt 或 Argon2
        // 这里仅作演示
        return "bcrypt:$2a$12$eIZjH.hs/NQQcr8YpBXV6O/rLj/8UChDKCT5IvFWVr7q3l0Y1tWCG";
    }
    
    /**
     * 改进版本：不存储原始密钥
     */
    public static String encryptDataSecure(String plaintext, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, 0, key.length, "AES");
        
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Cryptographic Failures Demo ===");
        
        String data = "Sensitive Data";
        System.out.println("Original: " + data);
        
        // 漏洞演示
        String encrypted = encryptDataVulnerable(data);
        System.out.println("Vulnerable Encrypted: " + encrypted);
        
        String password = "MyPassword123";
        String hashed = hashPasswordVulnerable(password);
        System.out.println("Vulnerable Hashed Password: " + hashed);
        
        System.out.println("\n=== 改进方案 ===");
        System.out.println("Secure Password Hash: " + hashPasswordSecure(password));
    }
}
