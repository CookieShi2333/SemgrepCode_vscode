package com.example.owasp.a10.ssrf;

import java.net.*;
import java.io.*;

/**
 * A10:2021 – Server-Side Request Forgery (SSRF)
 * 漏洞示例2：通过 URL Redirect 跟随重定向，导致 SSRF
 */
public class SSRFViaRedirect {
    
    /**
     * 漏洞代码：自动跟随重定向，可被用于 SSRF 攻击
     * 攻击流程：
     * 1. 用户提供 URL：https://trusted-domain.com/api/fetch
     * 2. 服务器跟随重定向
     * 3. 攻击者在 trusted-domain.com 上放置重定向到 http://localhost:6379/
     */
    public static String fetchWithRedirectVulnerable(String userUrl) {
        try {
            URL url = new URL(userUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(true);  // 自动跟随重定向！
            connection.setConnectTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            
            InputStream is = connection.getInputStream();
            StringBuilder response = new StringBuilder();
            int character;
            while ((character = is.read()) != -1) {
                response.append((char) character);
                if (response.length() > 5000) break;
            }
            
            System.out.println("Response Code: " + responseCode);
            return response.toString();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本1：限制重定向次数
     */
    public static String fetchWithLimitedRedirect(String userUrl) {
        try {
            URL url = new URL(userUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // 限制重定向次数为3次
            int redirectCount = 0;
            int maxRedirects = 3;
            
            while (redirectCount < maxRedirects) {
                connection.setConnectTimeout(5000);
                int responseCode = connection.getResponseCode();
                
                // 检查是否为重定向
                if (responseCode >= 300 && responseCode < 400) {
                    String location = connection.getHeaderField("Location");
                    if (location == null) {
                        break;
                    }
                    
                    // 验证重定向 URL
                    URL redirectUrl = new URL(location);
                    if (!isUrlAllowed(redirectUrl)) {
                        throw new SecurityException("Redirect to unauthorized URL: " + location);
                    }
                    
                    url = redirectUrl;
                    connection = (HttpURLConnection) url.openConnection();
                    redirectCount++;
                } else {
                    break;
                }
            }
            
            if (redirectCount >= maxRedirects) {
                throw new SecurityException("Too many redirects");
            }
            
            InputStream is = connection.getInputStream();
            StringBuilder response = new StringBuilder();
            int character;
            while ((character = is.read()) != -1) {
                response.append((char) character);
                if (response.length() > 5000) break;
            }
            
            return response.toString();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本2：禁用自动重定向，手动管理
     */
    public static String fetchWithManualRedirectHandling(String userUrl, String originalHost) {
        try {
            URL url = new URL(userUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);  // 禁用自动重定向
            connection.setConnectTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            
            // 如果是重定向，检查是否允许
            if (responseCode >= 300 && responseCode < 400) {
                String location = connection.getHeaderField("Location");
                
                if (location == null) {
                    throw new SecurityException("Invalid redirect");
                }
                
                URL redirectUrl = new URL(location);
                
                // 只允许同源重定向
                if (!redirectUrl.getHost().equals(new URL(userUrl).getHost())) {
                    throw new SecurityException("Cross-origin redirect not allowed");
                }
                
                // 不允许重定向到私有 IP
                if (isPrivateIp(redirectUrl.getHost())) {
                    throw new SecurityException("Redirect to private IP not allowed");
                }
                
                return "Redirect detected and allowed: " + location;
            }
            
            InputStream is = connection.getInputStream();
            StringBuilder response = new StringBuilder();
            int character;
            while ((character = is.read()) != -1) {
                response.append((char) character);
                if (response.length() > 5000) break;
            }
            
            return response.toString();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 验证 URL 是否被允许
     */
    private static boolean isUrlAllowed(URL url) {
        String host = url.getHost();
        
        // 检查私有 IP
        if (isPrivateIp(host)) {
            return false;
        }
        
        // 检查协议
        String protocol = url.getProtocol();
        if (!protocol.equals("http") && !protocol.equals("https")) {
            return false;
        }
        
        // 检查端口
        int port = url.getPort();
        if (port != -1 && port != 80 && port != 443) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 检查是否为私有 IP 地址
     */
    private static boolean isPrivateIp(String host) {
        if (host.equals("localhost") || host.equals("127.0.0.1")) {
            return true;
        }
        
        if (host.startsWith("192.168.") ||
            host.startsWith("10.") ||
            host.startsWith("172.")) {
            return true;
        }
        
        if (host.equals("169.254.169.254")) {
            return true;
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        System.out.println("=== SSRF via Redirect Demo ===\n");
        
        System.out.println("=== Attack Scenario ===");
        System.out.println("1. User provides: https://trusted-domain.com/api/fetch");
        System.out.println("2. Attacker controls trusted-domain.com");
        System.out.println("3. Attacker redirects to: http://localhost:6379/");
        System.out.println("4. Server follows redirect and queries Redis\n");
        
        System.out.println("=== Vulnerable Code ===");
        System.out.println("HttpURLConnection.setInstanceFollowRedirects(true)");
        System.out.println("Automatically follows ALL redirects without validation\n");
        
        System.out.println("=== Defense Mechanisms ===");
        System.out.println("1. Limit redirect count (max 3)");
        System.out.println("2. Validate each redirect URL");
        System.out.println("3. Disable auto-redirect, handle manually");
        System.out.println("4. Block redirects to private IPs");
        System.out.println("5. Only allow same-origin redirects");
    }
}
