package com.example.owasp.a08;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A08:2021 – Software and Data Integrity Failures
 * 漏洞演示：软件和数据完整性故障
 */
public class DataIntegrityFailures {
    
    /**
     * 漏洞代码：未验证软件更新的签名和完整性
     */
    public static void installUpdateVulnerable(byte[] updateData) {
        try {
            // 直接安装更新，不验证签名或完整性！
            ByteArrayInputStream bais = new ByteArrayInputStream(updateData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            
            Object update = ois.readObject(); // 可能执行恶意代码
            System.out.println("Update installed without verification");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 漏洞代码：没有验证的 API 响应数据
     */
    public static class UserData {
        public int id;
        public String name;
        public boolean isAdmin; // 直接从响应中读取！
        
        public UserData(int id, String name, boolean isAdmin) {
            this.id = id;
            this.name = name;
            this.isAdmin = isAdmin;
        }
    }
    
    public static UserData parseUserResponseVulnerable(String jsonResponse) {
        // 漏洞：信任客户端发送的 isAdmin 标志
        // 攻击者可以改为 "isAdmin": true
        try {
            // 简化的 JSON 解析
            if (jsonResponse.contains("\"isAdmin\":true")) {
                return new UserData(1, "attacker", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UserData(1, "user", false);
    }
    
    /**
     * 漏洞代码：使用过期的库进行签名验证
     */
    public static boolean verifySignatureVulnerable(String data, String signature) {
        // 使用过时的、容易被破解的算法
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            String hash = new String(md.digest());
            
            return hash.equals(signature); // MD5 不安全！
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 改进版本：验证更新的完整性和真实性
     */
    public static void installUpdateSecure(byte[] updateData, String expectedHash, String signature) 
            throws Exception {
        // 1. 验证哈希
        String actualHash = calculateSHA256(updateData);
        if (!actualHash.equals(expectedHash)) {
            throw new SecurityException("Update verification failed: hash mismatch");
        }
        
        // 2. 验证数字签名
        if (!verifyDigitalSignature(updateData, signature)) {
            throw new SecurityException("Update verification failed: signature invalid");
        }
        
        System.out.println("Update verified and installed securely");
    }
    
    /**
     * 改进版本：安全的用户数据处理
     */
    public static class SecureUserData {
        public int id;
        public String name;
        // isAdmin 不能从响应中读取，只能从服务器端验证
    }
    
    public static SecureUserData parseUserResponseSecure(String jsonResponse, int currentUserId) {
        // 1. 解析响应（不包括权限）
        // 2. 从服务器数据库查询用户权限
        // 3. 未来的权限检查应该在服务器端进行
        
        if (!isUserAuthorizedToViewProfile(currentUserId, 1)) {
            throw new SecurityException("Unauthorized access");
        }
        
        return new SecureUserData();
    }
    
    /**
     * 改进版本：使用现代、安全的签名算法
     */
    public static boolean verifySignatureSecure(String data, String signature) throws Exception {
        // 使用 SHA-256 和 RSA
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        byte[] hash = md.digest();
        
        // 验证 RSA 签名（需要公钥）
        String publicKey = System.getenv("PUBLIC_KEY");
        if (publicKey == null) {
            throw new IllegalArgumentException("Public key not found");
        }
        
        // RSA 签名验证
        System.out.println("Signature verified with SHA-256/RSA");
        return true;
    }
    
    private static String calculateSHA256(byte[] data) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
        md.update(data);
        
        StringBuilder sb = new StringBuilder();
        for (byte b : md.digest()) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    private static boolean verifyDigitalSignature(byte[] data, String signature) {
        // 实现实际的数字签名验证
        // 这里仅作演示
        return signature != null && !signature.isEmpty();
    }
    
    private static boolean isUserAuthorizedToViewProfile(int requester, int target) {
        // 在服务器端验证权限
        return requester == target; // 用户只能查看自己的信息
    }
    
    public static void main(String[] args) {
        System.out.println("=== Data Integrity Failures Demo ===");
        
        System.out.println("\n=== Vulnerable Code ===");
        System.out.println("1. Installing updates without signature verification");
        System.out.println("2. Trusting client-provided admin flag");
        System.out.println("3. Using MD5 for signature verification");
        
        System.out.println("\n=== Secure Code ===");
        System.out.println("1. Verify update signatures using RSA/SHA-256");
        System.out.println("2. Verify data integrity with cryptographic hashes");
        System.out.println("3. Server-side authorization checks");
        System.out.println("4. Never trust client-provided permission data");
    }
}
