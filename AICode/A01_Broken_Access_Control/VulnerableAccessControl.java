package com.example.owasp.a01;

import java.util.*;

/**
 * A01:2021 – Broken Access Control
 * 漏洞演示：未正确检查用户权限，允许普通用户访问管理员功能
 */
public class VulnerableAccessControl {
    
    private static Map<Integer, User> users = new HashMap<>();
    
    static {
        users.put(1, new User(1, "alice", "USER"));
        users.put(2, new User(2, "bob", "ADMIN"));
    }
    
    /**
     * 漏洞代码：直接根据用户ID删除账户，未检查权限
     */
    public static void deleteUserAccount(int userId) {
        // 缺少权限检查
        users.remove(userId);
        System.out.println("User " + userId + " deleted");
    }
    
    /**
     * 改进版本：添加权限检查
     */
    public static void deleteUserAccountSecure(int currentUserId, int targetUserId) {
        User currentUser = users.get(currentUserId);
        
        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            throw new SecurityException("Insufficient permissions to delete user");
        }
        
        users.remove(targetUserId);
        System.out.println("User " + targetUserId + " deleted by admin");
    }
    
    static class User {
        int id;
        String name;
        String role;
        
        User(int id, String name, String role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }
        
        public String getRole() {
            return role;
        }
    }
    
    public static void main(String[] args) {
        // 漏洞演示：普通用户可以删除任何账户
        System.out.println("=== Vulnerable Code ===");
        deleteUserAccount(2); // 用户1（普通用户）可以删除用户2（管理员）
        
        System.out.println("\n=== Secure Code ===");
        try {
            deleteUserAccountSecure(1, 2); // 权限检查阻止普通用户删除
        } catch (SecurityException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
