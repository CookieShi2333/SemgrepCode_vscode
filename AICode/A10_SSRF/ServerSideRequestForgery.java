package com.example.owasp.a10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * A10:2021 – Server-Side Request Forgery (SSRF)
 * 漏洞演示：服务端请求伪造
 */
public class ServerSideRequestForgery {
    
    /**
     * 漏洞代码：直接从用户输入获取 URL 并发送请求
     */
    public static String fetchUrlVulnerable(String userProvidedUrl) {
        try {
            // 漏洞：没有验证 URL，攻击者可以指向内部服务
            URL url = new URL(userProvidedUrl);
            
            URLConnection connection = url.openConnection();
            ((sun.net.www.protocol.http.HttpURLConnection) connection).setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            return response.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 漏洞代码：通过 SSRF 访问内部元数据服务
     * 攻击示例：http://169.254.169.254/latest/meta-data/ (AWS EC2 元数据)
     */
    public static String getInternalMetadataVulnerable() {
        return fetchUrlVulnerable("http://169.254.169.254/latest/meta-data/iam/security-credentials/");
    }
    
    /**
     * 漏洞代码：通过 SSRF 访问受限的内部服务
     * 攻击示例：http://localhost:6379/ (Redis 服务)
     */
    public static String accessInternalServicesVulnerable() {
        return fetchUrlVulnerable("http://localhost:8080/admin/");
    }
    
    /**
     * 改进版本：URL 白名单验证
     */
    public static String fetchUrlSecure(String userProvidedUrl) {
        try {
            // 1. 验证 URL 格式
            URL url = new URL(userProvidedUrl);
            
            // 2. 白名单检查 - 只允许特定的域名
            String[] allowedDomains = {
                    "api.example.com",
                    "www.example.com",
                    "cdn.example.com"
            };
            
            boolean isAllowed = false;
            for (String domain : allowedDomains) {
                if (url.getHost().equals(domain)) {
                    isAllowed = true;
                    break;
                }
            }
            
            if (!isAllowed) {
                throw new SecurityException("URL not in whitelist");
            }
            
            // 3. 检查端口（只允许 80 和 443）
            int port = url.getPort();
            if (port != -1 && port != 80 && port != 443) {
                throw new SecurityException("Invalid port");
            }
            
            // 4. 检查协议
            String protocol = url.getProtocol();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                throw new SecurityException("Invalid protocol");
            }
            
            // 5. 防止访问内部 IP 地址
            String host = url.getHost();
            if (isPrivateIpAddress(host)) {
                throw new SecurityException("Access to private IP addresses not allowed");
            }
            
            // 6. 设置超时以防止长时间挂起
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            return response.toString();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * 改进版本：使用预定义的端点而不是用户提供的 URL
     */
    public static String fetchDataSecure(String endpoint) {
        // 定义允许的端点
        String[] allowedEndpoints = {
                "/api/users",
                "/api/products",
                "/api/orders"
        };
        
        // 检查端点是否在允许列表中
        boolean isAllowed = false;
        for (String allowed : allowedEndpoints) {
            if (endpoint.equals(allowed)) {
                isAllowed = true;
                break;
            }
        }
        
        if (!isAllowed) {
            throw new SecurityException("Endpoint not allowed");
        }
        
        // 构建完整 URL 并发送请求
        String baseUrl = "https://api.example.com";
        String fullUrl = baseUrl + endpoint;
        
        return fetchUrlSecure(fullUrl);
    }
    
    /**
     * 检查是否为私有 IP 地址
     */
    private static boolean isPrivateIpAddress(String host) {
        // 检查常见的私有 IP 范围
        if (host.equals("localhost") || host.equals("127.0.0.1")) {
            return true;
        }
        if (host.startsWith("192.168.") || 
            host.startsWith("10.") || 
            host.startsWith("172.")) {
            return true;
        }
        // 检查链路本地地址和元数据服务
        if (host.equals("169.254.169.254")) {
            return true;
        }
        return false;
    }
    
    /**
     * 改进版本：使用反向代理或 API 网关来防止 SSRF
     */
    public static class SecureApiClient {
        private static final String API_GATEWAY_URL = "https://api-gateway.example.com";
        
        public static String fetchData(String resource) {
            // 所有请求都通过网关，网关进行额外的验证
            String gatewayUrl = API_GATEWAY_URL + "?resource=" + resource;
            return fetchUrlSecure(gatewayUrl);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Server-Side Request Forgery (SSRF) Demo ===");
        
        System.out.println("\n=== Vulnerable Code ===");
        System.out.println("1. Direct URL from user input: http://localhost:6379/");
        System.out.println("   Result: Access Redis on internal network");
        System.out.println("2. AWS metadata: http://169.254.169.254/latest/meta-data/");
        System.out.println("   Result: Leak AWS credentials and sensitive info");
        System.out.println("3. Internal admin panel: http://localhost:8080/admin/");
        System.out.println("   Result: Bypass authentication");
        
        System.out.println("\n=== Secure Code ===");
        System.out.println("1. URL whitelist validation");
        System.out.println("2. Block private IP addresses (127.0.0.1, 192.168.*, etc.)");
        System.out.println("3. Restrict to specific protocols (HTTP/HTTPS only)");
        System.out.println("4. Block specific ports (only 80, 443)");
        System.out.println("5. Set connection timeouts");
        System.out.println("6. Use predefined endpoints instead of user URLs");
        System.out.println("7. Use API gateway for additional validation");
    }
}
