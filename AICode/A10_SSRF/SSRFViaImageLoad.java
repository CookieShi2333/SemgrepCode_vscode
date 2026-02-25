package com.example.owasp.a10.ssrf;

import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * A10:2021 – Server-Side Request Forgery (SSRF)
 * 漏洞示例5：通过图片加载的 SSRF
 * 应用允许用户上传图片 URL，使用 ImageIO 加载，攻击者可以指向内部资源
 */
public class SSRFViaImageLoad {
    
    /**
     * 漏洞代码：直接从用户提供的 URL 加载图片
     * 攻击示例：
     * 1. file:///etc/passwd (读取本地文件)
     * 2. http://localhost:6379/ (扫描内部服务)
     * 3. http://169.254.169.254/latest/meta-data/ (获取云凭证)
     */
    public static BufferedImage loadImageVulnerable(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            
            System.out.println("Image loaded from: " + imageUrl);
            return image;
            
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 漏洞代码2：通过 URLConnection 加载图片
     * 同样存在 SSRF 风险
     */
    public static InputStream loadImageFromUrlVulnerable(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            
            return connection.getInputStream();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 改进版本1：URL 白名单验证
     */
    public static BufferedImage loadImageWithWhitelist(String imageUrl) {
        try {
            // 验证 URL 白名单
            if (!isImageUrlAllowed(imageUrl)) {
                throw new SecurityException("URL not allowed");
            }
            
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            
            return image;
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 改进版本2：验证响应内容类型
     */
    public static BufferedImage loadImageWithContentTypeValidation(String imageUrl) {
        try {
            if (!isImageUrlAllowed(imageUrl)) {
                throw new SecurityException("URL not allowed");
            }
            
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            // 验证响应头中的 Content-Type
            String contentType = connection.getContentType();
            if (contentType == null || !isValidImageContentType(contentType)) {
                throw new SecurityException("Invalid content type: " + contentType);
            }
            
            // 限制响应大小（防止 Zip bomb 类型攻击）
            int contentLength = connection.getContentLength();
            if (contentLength > 10 * 1024 * 1024) { // 10MB limit
                throw new SecurityException("Content too large: " + contentLength);
            }
            
            BufferedImage image = ImageIO.read(connection.getInputStream());
            return image;
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 改进版本3：使用代理或网关处理图片
     */
    public static BufferedImage loadImageViaProxy(String imageUrl) {
        try {
            // 所有图片加载都通过内部代理进行
            String proxyService = "https://image-proxy.example.com/load";
            
            String encodedUrl = URLEncoder.encode(imageUrl, "UTF-8");
            URL proxyUrl = new URL(proxyService + "?url=" + encodedUrl);
            
            HttpURLConnection connection = (HttpURLConnection) proxyUrl.openConnection();
            connection.setConnectTimeout(5000);
            
            // 验证代理返回的内容
            String contentType = connection.getContentType();
            if (!isValidImageContentType(contentType)) {
                throw new SecurityException("Invalid content type from proxy");
            }
            
            BufferedImage image = ImageIO.read(connection.getInputStream());
            return image;
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 改进版本4：下载到临时文件后再加载
     */
    public static BufferedImage loadImageSafely(String imageUrl) {
        File tempFile = null;
        try {
            if (!isImageUrlAllowed(imageUrl)) {
                throw new SecurityException("URL not allowed");
            }
            
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            // 验证 Content-Type
            String contentType = connection.getContentType();
            if (!isValidImageContentType(contentType)) {
                throw new SecurityException("Invalid content type: " + contentType);
            }
            
            // 验证内容大小
            int contentLength = connection.getContentLength();
            if (contentLength > 10 * 1024 * 1024) {
                throw new SecurityException("Content too large");
            }
            
            // 创建临时文件
            tempFile = File.createTempFile("image_", ".tmp");
            
            // 将内容下载到临时文件
            try (InputStream is = connection.getInputStream();
                 FileOutputStream fos = new FileOutputStream(tempFile)) {
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalBytes = 0;
                
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                    
                    // 再次检查大小
                    if (totalBytes > 10 * 1024 * 1024) {
                        throw new SecurityException("Downloaded content exceeded size limit");
                    }
                }
            }
            
            // 从临时文件加载图片
            BufferedImage image = ImageIO.read(tempFile);
            return image;
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } finally {
            // 清理临时文件
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
    
    /**
     * 检查 URL 是否被允许
     */
    private static boolean isImageUrlAllowed(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            
            // 检查协议
            String protocol = url.getProtocol();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                return false;
            }
            
            // 检查主机是否为私有 IP
            String host = url.getHost();
            if (isPrivateIp(host)) {
                return false;
            }
            
            // 检查主机白名单
            String[] allowedHosts = {
                    "cdn.example.com",
                    "images.example.com",
                    "api.example.com"
            };
            
            for (String allowed : allowedHosts) {
                if (host.equals(allowed)) {
                    return true;
                }
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 检查是否为有效的图片 Content-Type
     */
    private static boolean isValidImageContentType(String contentType) {
        if (contentType == null) {
            return false;
        }
        
        String[] validTypes = {
                "image/jpeg",
                "image/png",
                "image/gif",
                "image/webp",
                "image/svg+xml"
        };
        
        for (String type : validTypes) {
            if (contentType.startsWith(type)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 检查是否为私有 IP
     */
    private static boolean isPrivateIp(String host) {
        if (host.equals("localhost") || host.equals("127.0.0.1")) return true;
        if (host.equals("::1")) return true;
        if (host.startsWith("192.168.")) return true;
        if (host.startsWith("10.")) return true;
        if (host.startsWith("172.")) return true;
        if (host.equals("169.254.169.254")) return true;
        return false;
    }
    
    public static void main(String[] args) {
        System.out.println("=== SSRF via Image Loading Demo ===\n");
        
        System.out.println("=== Attack Scenarios ===");
        System.out.println("1. file:///etc/passwd");
        System.out.println("   Result: Read local files\n");
        
        System.out.println("2. http://localhost:6379/");
        System.out.println("   Result: Scan internal Redis\n");
        
        System.out.println("3. http://169.254.169.254/latest/meta-data/");
        System.out.println("   Result: Get cloud credentials\n");
        
        System.out.println("=== Defense Mechanisms ===");
        System.out.println("1. URL whitelist for allowed hosts");
        System.out.println("2. Validate Content-Type header");
        System.out.println("3. Limit response size");
        System.out.println("4. Use proxy/gateway for image loading");
        System.out.println("5. Block private IP addresses");
        System.out.println("6. Disable dangerous protocols (file, jar, etc.)");
    }
}
