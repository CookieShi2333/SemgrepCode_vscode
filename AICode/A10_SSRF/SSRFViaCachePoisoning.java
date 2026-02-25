package com.example.owasp.a10.ssrf;

import java.util.*;
import java.net.*;

/**
 * A10:2021 – Server-Side Request Forgery (SSRF)
 * 漏洞示例9：通过缓存破坏（Cache Poisoning）和时间竞争（TOCTOU）的 SSRF
 * 攻击者可以利用时间窗口破坏缓存或进行其他攻击
 */
public class SSRFViaCachePoisoning {
    
    /**
     * 简单的不安全缓存实现
     */
    static class UnsafeCache {
        private Map<String, CachedUrl> cache = new HashMap<>();
        
        // 漏洞：不安全的 TOCTOU（Time Of Check, Time Of Use）
        public String getOrFetch(String url) {
            CachedUrl cached = cache.get(url);
            
            // 检查时间
            if (cached != null && !cached.isExpired()) {
                System.out.println("Using cached result");
                return cached.content;
            }
            
            // 使用时间 - 检查和使用之间可能发生变化
            System.out.println("Fetching: " + url);
            String content = fetchUrl(url);
            
            cache.put(url, new CachedUrl(content));
            return content;
        }
        
        private String fetchUrl(String url) {
            // 简化的实现
            return "Content from: " + url;
        }
    }
    
    /**
     * 缓存项
     */
    static class CachedUrl {
        String content;
        long expiresAt;
        
        CachedUrl(String content) {
            this.content = content;
            this.expiresAt = System.currentTimeMillis() + 3600000; // 1 hour
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() > expiresAt;
        }
    }
    
    /**
     * 漏洞代码：不安全的缓存键生成
     * 攻击示例：
     * 1. 服务器缓存：http://example.com/ -> content
     * 2. 攻击者使用 Host 头注入：Host: example.com.attacker.com
     * 3. 中间代理可能被迷惑
     */
    public static String fetchWithWeakCacheKeyVulnerable(String inputUrl, String hostHeader) {
        try {
            URL url = new URL(inputUrl);
            
            // 漏洞：缓存键只基于 URL 路径，不考虑 Host 头
            String cacheKey = url.getPath();  // 容易被欺骗！
            
            // 检查缓存
            if (globalCache.containsKey(cacheKey)) {
                return globalCache.get(cacheKey);
            }
            
            // 获取内容
            String content = "Content";
            
            // 加入缓存
            globalCache.put(cacheKey, content);
            return content;
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    private static Map<String, String> globalCache = new HashMap<>();
    
    /**
     * 漏洞代码2：HTTP Response Splitting（通过缓存）
     */
    public static String fetchWithResponseSplittingVulnerable(String userUrl, String headerValue) {
        try {
            URL url = new URL(userUrl);
            
            // 从 URL 获取内容，但不验证返回的头部
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Custom", headerValue); // 包含 CRLF
            
            String responseContent = "Cached content";
            
            // 缓存整个响应，包括可能被注入的头部
            globalCache.put(userUrl, responseContent);
            
            return responseContent;
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本1：强化的缓存键生成
     */
    public static String fetchWithStrongCacheKey(String userUrl) {
        try {
            URL url = new URL(userUrl);
            
            // 验证 URL
            if (!isUrlAllowed(url)) {
                throw new SecurityException("URL not allowed");
            }
            
            // 强化的缓存键：协议 + 主机 + 路径 + 查询参数
            String cacheKey = generateStrongCacheKey(url);
            
            // 检查缓存
            CachedUrl cached = getCachedUrl(cacheKey);
            if (cached != null && !cached.isExpired()) {
                System.out.println("Using cached result");
                return cached.content;
            }
            
            // 获取内容
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(connection.getInputStream()));
            
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                if (content.length() > 50000) break;
            }
            
            // 只缓存有效的内容
            setCachedUrl(cacheKey, new CachedUrl(content.toString()));
            
            return content.toString();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本2：使用完整的 URL 规范化作为缓存键
     */
    public static String fetchWithNormalizedCacheKey(String userUrl) {
        try {
            URL url = new URL(userUrl);
            
            // 规范化 URL
            String normalizedUrl = normalizeUrl(url);
            
            // 验证规范化后的 URL
            if (!isUrlAllowed(new URL(normalizedUrl))) {
                throw new SecurityException("URL not allowed");
            }
            
            // 使用规范化的 URL 作为缓存键
            CachedUrl cached = getCachedUrl(normalizedUrl);
            if (cached != null && !cached.isExpired()) {
                return cached.content;
            }
            
            HttpURLConnection connection = (HttpURLConnection) new URL(normalizedUrl).openConnection();
            connection.setConnectTimeout(5000);
            
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(connection.getInputStream()));
            
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                if (content.length() > 50000) break;
            }
            
            setCachedUrl(normalizedUrl, new CachedUrl(content.toString()));
            return content.toString();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本3：线程安全的缓存操作
     */
    public static class ThreadSafeCache {
        private final Map<String, CachedUrl> cache = Collections.synchronizedMap(new HashMap<>());
        
        public synchronized String getOrFetch(String url) {
            CachedUrl cached = cache.get(url);
            
            if (cached != null && !cached.isExpired()) {
                return cached.content;
            }
            
            // 同步块防止 TOCTOU
            synchronized (this) {
                // 再次检查（Double-Check Locking）
                cached = cache.get(url);
                if (cached != null && !cached.isExpired()) {
                    return cached.content;
                }
                
                try {
                    String content = "Fetched content";
                    cache.put(url, new CachedUrl(content));
                    return content;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    
    /**
     * 生成强化的缓存键
     */
    private static String generateStrongCacheKey(URL url) {
        return url.getProtocol() + "://" +
               url.getHost() + ":" +
               (url.getPort() != -1 ? url.getPort() : 80) +
               url.getPath() +
               (url.getQuery() != null ? "?" + url.getQuery() : "");
    }
    
    /**
     * 规范化 URL
     */
    private static String normalizeUrl(URL url) {
        // 移除默认端口和规范化路径
        int port = url.getPort();
        String portStr = "";
        
        if (port != -1 && port != 80 && port != 443) {
            portStr = ":" + port;
        }
        
        return url.getProtocol() + "://" +
               url.getHost() + portStr +
               (url.getPath() != null ? url.getPath() : "/") +
               (url.getQuery() != null ? "?" + url.getQuery() : "");
    }
    
    /**
     * 验证 URL 是否允许
     */
    private static boolean isUrlAllowed(URL url) {
        // 检查协议
        if (!url.getProtocol().matches("https?")) {
            return false;
        }
        
        // 检查主机
        String host = url.getHost();
        if (host.equals("localhost") || host.equals("127.0.0.1")) {
            return false;
        }
        
        if (host.startsWith("192.168.") || host.startsWith("10.")) {
            return false;
        }
        
        return true;
    }
    
    private static Map<String, CachedUrl> privateCache = Collections.synchronizedMap(new HashMap<>());
    
    private static CachedUrl getCachedUrl(String key) {
        return privateCache.get(key);
    }
    
    private static void setCachedUrl(String key, CachedUrl value) {
        privateCache.put(key, value);
    }
    
    public static void main(String[] args) {
        System.out.println("=== SSRF via Cache Poisoning Demo ===\n");
        
        System.out.println("=== Attack Scenario 1: Weak Cache Key ===");
        System.out.println("1. Normal request: GET /api/data HTTP/1.1 Host: example.com");
        System.out.println("2. Cached as: /api/data");
        System.out.println("3. Attack request: Host: evil.com");
        System.out.println("4. Gets same cached response!\n");
        
        System.out.println("=== Attack Scenario 2: TOCTOU (Time Of Check, Time Of Use) ===");
        System.out.println("1. Check cache (miss)");
        System.out.println("2. Attacker modifies URL");
        System.out.println("3. Fetch happens with modified URL");
        System.out.println("4. Malicious content is cached\n");
        
        System.out.println("=== Defense Mechanisms ===");
        System.out.println("1. Use complete URL as cache key");
        System.out.println("2. Normalize URLs before caching");
        System.out.println("3. Synchronize check-and-use operations");
        System.out.println("4. Include all relevant factors in cache key");
        System.out.println("5. Use short TTL for cached content");
        System.out.println("6. Validate cached content before use");
    }
}
