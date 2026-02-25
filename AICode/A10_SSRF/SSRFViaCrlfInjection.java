package com.example.owasp.a10.ssrf;

import java.net.*;
import java.io.*;

/**
 * A10:2021 – Server-Side Request Forgery (SSRF)
 * 漏洞示例8：通过 CRLF 注入的 SSRF（HTTP Request Splitting）
 * 攻击者可以通过换行符注入额外的 HTTP 请求
 */
public class SSRFViaCrlfInjection {
    
    /**
     * 漏洞代码：用户输入直接用于 HTTP 头部
     * 攻击示例：
     * 用户输入：test%0d%0aContent-Length:0%0d%0a%0d%0aHTTP/1.1 200 OK
     * 这会被解析为两个独立的请求
     */
    public static String fetchWithHeadersVulnerable(String hostname, String path, 
                                                    String customHeader) {
        try {
            // 漏洞：直接拼接用户输入的头部
            String request = "GET " + path + " HTTP/1.1\r\n" +
                           "Host: " + hostname + "\r\n" +
                           "X-Custom-Header: " + customHeader + "\r\n" +  // 危险！
                           "Connection: close\r\n\r\n";
            
            Socket socket = new Socket(hostname, 80);
            OutputStream out = socket.getOutputStream();
            out.write(request.getBytes());
            
            InputStream in = socket.getInputStream();
            StringBuilder response = new StringBuilder();
            int character;
            while ((character = in.read()) != -1) {
                response.append((char) character);
            }
            
            socket.close();
            return response.toString();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 漏洞代码2：通过 Host 头注入
     * 攻击示例：
     * Host: evil.com:80%0aContent-Length:0%0a%0a 
     */
    public static String fetchWithInjectedHostVulnerable(String userHost, String path) {
        try {
            URL url = new URL("http://" + userHost + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // 漏洞：直接使用用户提供的 Host
            connection.setRequestProperty("Host", userHost);
            
            int responseCode = connection.getResponseCode();
            return "Response: " + responseCode;
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本1：验证和清理输入
     */
    public static String fetchWithCleanedHeaders(String hostname, String path, 
                                                 String customHeader) {
        try {
            // 验证主机名格式
            if (!isValidHostname(hostname)) {
                throw new SecurityException("Invalid hostname format");
            }
            
            // 验证路径格式
            if (!isValidPath(path)) {
                throw new SecurityException("Invalid path format");
            }
            
            // 清理自定义头部 - 移除 CRLF 字符
            String cleanHeader = sanitizeHeader(customHeader);
            
            // 验证清理后的头部
            if (containsCRLF(cleanHeader)) {
                throw new SecurityException("Header contains invalid characters");
            }
            
            String request = "GET " + path + " HTTP/1.1\r\n" +
                           "Host: " + hostname + "\r\n" +
                           "X-Custom-Header: " + cleanHeader + "\r\n" +
                           "Connection: close\r\n\r\n";
            
            Socket socket = new Socket(hostname, 80);
            OutputStream out = socket.getOutputStream();
            out.write(request.getBytes());
            
            InputStream in = socket.getInputStream();
            StringBuilder response = new StringBuilder();
            int character;
            while ((character = in.read()) != -1 && response.length() < 5000) {
                response.append((char) character);
            }
            
            socket.close();
            return response.toString();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本2：使用 Java 内置的 HttpURLConnection（更安全）
     */
    public static String fetchWithHttpUrlConnection(String urlString, String customHeader) {
        try {
            // 验证 URL
            URL url = new URL(urlString);
            
            if (!isValidUrl(url)) {
                throw new SecurityException("Invalid URL");
            }
            
            // 验证自定义头部
            String cleanHeader = sanitizeHeader(customHeader);
            if (containsCRLF(cleanHeader)) {
                throw new SecurityException("Header contains invalid characters");
            }
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // 设置头部 - HttpURLConnection 会自动处理格式
            connection.setRequestProperty("X-Custom-Header", cleanHeader);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            
            StringBuilder response = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
                if (response.length() > 5000) break;
            }
            
            return "Response Code: " + responseCode + "\n" + response.toString();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本3：使用 HTTP/2 或更安全的 HTTP 客户端
     */
    public static String fetchWithSecureClient(String urlString, String customHeader) {
        try {
            if (!isValidUrl(new URL(urlString))) {
                throw new SecurityException("Invalid URL");
            }
            
            String cleanHeader = sanitizeHeader(customHeader);
            if (containsCRLF(cleanHeader)) {
                throw new SecurityException("Header contains invalid characters");
            }
            
            // 使用 Java 11+ 的 HttpClient（更安全）
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(new URL(urlString).toURI())
                    .header("X-Custom-Header", cleanHeader)
                    .timeout(java.time.Duration.ofSeconds(5))
                    .GET()
                    .build();
            
            java.net.http.HttpResponse<String> response = client.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());
            
            return "Status: " + response.statusCode() + "\n" + response.body();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 移除或替换 CRLF 字符
     */
    private static String sanitizeHeader(String input) {
        if (input == null) {
            return "";
        }
        
        // 移除所有 CR 和 LF 字符
        return input.replaceAll("[\\r\\n]", "");
    }
    
    /**
     * 检查字符串是否包含 CRLF
     */
    private static boolean containsCRLF(String input) {
        return input != null && (input.contains("\r") || input.contains("\n"));
    }
    
    /**
     * 验证主机名格式
     */
    private static boolean isValidHostname(String hostname) {
        if (hostname == null || hostname.isEmpty()) {
            return false;
        }
        
        // 检查是否包含危险字符
        if (hostname.contains("\r") || hostname.contains("\n") || 
            hostname.contains(" ") || hostname.contains(":")) {
            return false;
        }
        
        // 简单的格式验证
        return hostname.matches("[a-zA-Z0-9.-]+");
    }
    
    /**
     * 验证路径格式
     */
    private static boolean isValidPath(String path) {
        if (path == null || !path.startsWith("/")) {
            return false;
        }
        
        // 检查是否包含危险字符
        if (path.contains("\r") || path.contains("\n")) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 验证 URL 是否安全
     */
    private static boolean isValidUrl(URL url) {
        String protocol = url.getProtocol();
        String host = url.getHost();
        
        // 只允许 HTTP 和 HTTPS
        if (!protocol.equals("http") && !protocol.equals("https")) {
            return false;
        }
        
        // 检查是否为私有 IP
        if (isPrivateIp(host)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 检查是否为私有 IP
     */
    private static boolean isPrivateIp(String host) {
        if (host.equals("localhost") || host.equals("127.0.0.1")) return true;
        if (host.startsWith("192.168.")) return true;
        if (host.startsWith("10.")) return true;
        if (host.startsWith("172.")) return true;
        if (host.equals("169.254.169.254")) return true;
        return false;
    }
    
    public static void main(String[] args) {
        System.out.println("=== SSRF via CRLF Injection Demo ===\n");
        
        System.out.println("=== Vulnerable Input ===");
        System.out.println("Custom Header: test%0d%0aContent-Length:0%0d%0a%0a");
        System.out.println("Decoded: test\\r\\nContent-Length:0\\r\\n\\n");
        System.out.println("Effect: Injects additional HTTP headers\n");
        
        System.out.println("=== Defense Mechanisms ===");
        System.out.println("1. Strip/remove CRLF characters from all input");
        System.out.println("2. Validate URL format strictly");
        System.out.println("3. Use HttpURLConnection (auto-formats headers)");
        System.out.println("4. Use Java 11+ HttpClient (more secure)");
        System.out.println("5. Validate headers against whitelist");
        System.out.println("6. Use URL encoding for parameters");
    }
}
