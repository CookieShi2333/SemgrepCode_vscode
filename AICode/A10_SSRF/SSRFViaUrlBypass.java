package com.example.owasp.a10.ssrf;

import java.net.*;

/**
 * A10:2021 – Server-Side Request Forgery (SSRF)
 * 漏洞示例4：通过 URL 编码和协议处理绕过防护
 */
public class SSRFViaUrlBypass {
    
    /**
     * 漏洞代码1：URL 编码绕过检查
     * 攻击示例：
     * - 输入：http://localhost%2Eexample.com/  (被编码的点)
     * - 检查：localhost.example.com (看起来合法)
     * - 实际连接：localhost
     */
    public static String fetchUrlVulnerableToEncoding(String userUrl) {
        try {
            URL url = new URL(userUrl);
            String host = url.getHost();
            
            // 检查是否包含 localhost
            if (host.contains("localhost") || host.contains("127.0.0.1")) {
                throw new SecurityException("localhost not allowed");
            }
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            return "Success";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 漏洞代码2：协议处理不当
     * 攻击示例：
     * - file:///etc/passwd  (读取本地文件)
     * - jar:http://localhost/app.jar!/  (JAR 协议)
     * - data:text/plain,<script>alert('xss')</script>
     */
    public static String fetchUrlVulnerableToProtocols(String userUrl) {
        try {
            URL url = new URL(userUrl);
            
            // 只检查协议为 http 或 https，但其他协议也支持
            String protocol = url.getProtocol();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                throw new SecurityException("Only HTTP/HTTPS allowed");
            }
            
            // 其他危险协议可能被支持！
            URLConnection connection = url.openConnection();
            
            return "Success";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本1：正确处理 URL 编码
     */
    public static String fetchUrlWithProperDecoding(String userUrl) {
        try {
            // 先解码 URL
            String decodedUrl = URLDecoder.decode(userUrl, "UTF-8");
            
            URL url = new URL(decodedUrl);
            String host = url.getHost();
            
            // 对解码后的 URL 进行检查
            if (isBlockedHost(host)) {
                throw new SecurityException("Host is blocked: " + host);
            }
            
            // 再次对原始 URL 进行检查（防止双重编码）
            URL originalUrl = new URL(userUrl);
            if (isBlockedHost(originalUrl.getHost())) {
                throw new SecurityException("Host is blocked in original URL");
            }
            
            HttpURLConnection connection = (HttpURLConnection) originalUrl.openConnection();
            return "Success";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本2：严格的协议白名单
     */
    public static String fetchUrlWithProtocolWhitelist(String userUrl) {
        try {
            URL url = new URL(userUrl);
            
            // 严格的协议白名单
            String[] allowedProtocols = {"http", "https"};
            String protocol = url.getProtocol();
            
            boolean isAllowed = false;
            for (String allowed : allowedProtocols) {
                if (protocol.equals(allowed)) {
                    isAllowed = true;
                    break;
                }
            }
            
            if (!isAllowed) {
                throw new SecurityException("Protocol not allowed: " + protocol);
            }
            
            // 额外检查：某些 Java 版本支持危险协议
            if (protocol.equals("jar") || protocol.equals("file") || 
                protocol.equals("data") || protocol.equals("jndi")) {
                throw new SecurityException("Dangerous protocol: " + protocol);
            }
            
            String host = url.getHost();
            if (isBlockedHost(host)) {
                throw new SecurityException("Host is blocked: " + host);
            }
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            return "Success with protocol validation";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本3：规范化 URL 再检查
     */
    public static String fetchUrlWithNormalization(String userUrl) {
        try {
            // 规范化 URL（处理复杂的编码和路径）
            URI uri = new URI(userUrl);
            URL url = uri.normalize().toURL();
            
            // 对规范化后的 URL 进行检查
            String host = url.getHost();
            String protocol = url.getProtocol();
            
            // 检查协议
            if (!protocol.equals("http") && !protocol.equals("https")) {
                throw new SecurityException("Invalid protocol: " + protocol);
            }
            
            // 检查主机
            if (isBlockedHost(host)) {
                throw new SecurityException("Host is blocked: " + host);
            }
            
            // 检查路径是否尝试访问敏感资源
            String path = url.getPath();
            if (path.contains("..") || path.contains("//")) {
                throw new SecurityException("Suspicious path: " + path);
            }
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            
            return "Success with URL normalization";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本4：使用 URI 解析器而非 URL
     */
    public static String fetchResourceWithUriValidation(String userUrl) {
        try {
            // 使用 URI 而不是 URL 进行更严格的验证
            URI uri = new URI(userUrl);
            
            // 检查 scheme
            String scheme = uri.getScheme();
            if (scheme == null || (!scheme.equals("http") && !scheme.equals("https"))) {
                throw new SecurityException("Invalid or missing scheme");
            }
            
            // 检查 host
            String host = uri.getHost();
            if (host == null || host.isEmpty()) {
                throw new SecurityException("Missing or invalid host");
            }
            
            if (isBlockedHost(host)) {
                throw new SecurityException("Host is blocked: " + host);
            }
            
            // 将 URI 转换为 URL
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            return "Success with URI validation";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 检查主机是否被阻止
     */
    private static boolean isBlockedHost(String host) {
        if (host == null) {
            return true;
        }
        
        // 检查私有 IP
        if (host.equals("localhost") || host.equals("127.0.0.1")) return true;
        if (host.equals("::1")) return true;
        if (host.startsWith("192.168.")) return true;
        if (host.startsWith("10.")) return true;
        if (host.startsWith("172.")) return true;
        if (host.equals("169.254.169.254")) return true;
        
        // 检查保留主机名
        if (host.equals("example.com") || host.equals("test.com")) return true;
        
        return false;
    }
    
    public static void main(String[] args) {
        System.out.println("=== SSRF via URL Bypass Demo ===\n");
        
        System.out.println("=== Encoding/Bypass Attacks ===");
        System.out.println("1. URL Encoding: http://localhost%2e.example.com/");
        System.out.println("   Result: Bypass localhost check\n");
        
        System.out.println("2. Protocol Escape: jar:file:///etc/passwd");
        System.out.println("   Result: Read local files\n");
        
        System.out.println("3. Path Traversal: http://example.com/..\\\\..\\\\admin/");
        System.out.println("   Result: Access restricted paths\n");
        
        System.out.println("=== Defense Mechanisms ===");
        System.out.println("1. Decode URL before validation");
        System.out.println("2. Normalize URL paths");
        System.out.println("3. Use URI class for parsing");
        System.out.println("4. Whitelist protocols strictly");
        System.out.println("5. Validate both encoded and decoded forms");
    }
}
