package com.example.owasp.a10.ssrf;

import java.net.*;

/**
 * A10:2021 – Server-Side Request Forgery (SSRF)
 * 漏洞示例3：通过 DNS Rebinding 绕过防护
 */
public class SSRFViaDnsRebind {
    
    /**
     * 漏洞代码：简单的 DNS 查询可被攻击者控制
     * DNS Rebinding 攻击：
     * 1. 第一次查询：attacker.com -> 攻击者 IP (10.0.0.1)
     * 2. 第二次查询：attacker.com -> 内网 IP (192.168.1.100)
     * 3. 应用在验证时用第一个 IP，但实际请求用第二个 IP
     */
    public static String fetchUrlVulnerableToDnsRebind(String userUrl) {
        try {
            URL url = new URL(userUrl);
            String host = url.getHost();
            
            // 第一次 DNS 查询 - 白名单检查
            InetAddress addr = InetAddress.getByName(host);
            if (addr.getHostAddress().startsWith("192.168.") ||
                addr.getHostAddress().startsWith("10.")) {
                throw new SecurityException("Private IP not allowed");
            }
            
            // 第二次 DNS 查询 - 此时 DNS 已改变！
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response: " + responseCode);
            
            return "Success";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本1：使用 DNS 缓存防止 rebind
     */
    public static String fetchUrlWithDnsCaching(String userUrl) {
        try {
            URL url = new URL(userUrl);
            String host = url.getHost();
            
            // 使用缓存的 DNS 结果
            DnsCache cache = DnsCache.getInstance();
            String cachedIp = cache.resolveHost(host);
            
            if (cachedIp == null) {
                // 第一次解析
                InetAddress addr = InetAddress.getByName(host);
                cachedIp = addr.getHostAddress();
                
                // 检查是否为私有 IP
                if (isPrivateIp(cachedIp)) {
                    throw new SecurityException("Private IP not allowed: " + cachedIp);
                }
                
                // 缓存结果，防止后续查询返回不同的 IP
                cache.cacheHost(host, cachedIp, 3600); // 1小时 TTL
            }
            
            // 验证 IP 未改变
            InetAddress checkAddr = InetAddress.getByName(host);
            if (!checkAddr.getHostAddress().equals(cachedIp)) {
                throw new SecurityException("DNS rebind attack detected!");
            }
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            
            return "Success with cached DNS";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本2：绑定到特定 IP 而非主机名
     */
    public static String fetchUrlWithIpBinding(String userUrl) {
        try {
            URL url = new URL(userUrl);
            String host = url.getHost();
            
            // 在连接前解析 IP
            InetAddress addr = InetAddress.getByName(host);
            String resolvedIp = addr.getHostAddress();
            
            // 检查 IP 是否为私有
            if (isPrivateIp(resolvedIp)) {
                throw new SecurityException("Private IP not allowed: " + resolvedIp);
            }
            
            // 使用 IP 而不是主机名进行连接
            URL ipUrl = new URL(url.getProtocol(), 
                              resolvedIp, 
                              url.getPort(), 
                              url.getFile());
            
            // 设置 Host 头以维持虚拟主机支持
            HttpURLConnection connection = (HttpURLConnection) ipUrl.openConnection();
            connection.setRequestProperty("Host", host);
            connection.setConnectTimeout(5000);
            
            // 验证 SSL 证书（如果是 HTTPS）
            if (url.getProtocol().equals("https")) {
                verifyHostname(host, (javax.net.ssl.HttpsURLConnection) connection);
            }
            
            return "Success with IP binding";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本3：速率限制 DNS 查询
     */
    public static String fetchUrlWithDnsRateLimit(String userUrl) {
        try {
            URL url = new URL(userUrl);
            String host = url.getHost();
            
            // 检查是否为同一主机的多次请求
            DnsRateLimiter rateLimiter = DnsRateLimiter.getInstance();
            if (!rateLimiter.allowDnsQuery(host)) {
                throw new SecurityException("DNS rate limit exceeded for: " + host);
            }
            
            // 进行 DNS 查询
            InetAddress addr = InetAddress.getByName(host);
            
            if (isPrivateIp(addr.getHostAddress())) {
                throw new SecurityException("Private IP not allowed");
            }
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            
            return "Success with rate limiting";
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 简单的 DNS 缓存实现
     */
    static class DnsCache {
        private static DnsCache instance = new DnsCache();
        private java.util.Map<String, CacheEntry> cache = new java.util.HashMap<>();
        
        static DnsCache getInstance() {
            return instance;
        }
        
        String resolveHost(String host) {
            CacheEntry entry = cache.get(host);
            if (entry != null && !entry.isExpired()) {
                return entry.ip;
            }
            return null;
        }
        
        void cacheHost(String host, String ip, int ttlSeconds) {
            cache.put(host, new CacheEntry(ip, System.currentTimeMillis() + ttlSeconds * 1000));
        }
        
        static class CacheEntry {
            String ip;
            long expiresAt;
            
            CacheEntry(String ip, long expiresAt) {
                this.ip = ip;
                this.expiresAt = expiresAt;
            }
            
            boolean isExpired() {
                return System.currentTimeMillis() > expiresAt;
            }
        }
    }
    
    /**
     * DNS 请求速率限制
     */
    static class DnsRateLimiter {
        private static DnsRateLimiter instance = new DnsRateLimiter();
        private java.util.Map<String, Long> lastQuery = new java.util.HashMap<>();
        private final long MIN_INTERVAL = 1000; // 同一主机最少间隔 1 秒
        
        static DnsRateLimiter getInstance() {
            return instance;
        }
        
        boolean allowDnsQuery(String host) {
            Long lastTime = lastQuery.get(host);
            long now = System.currentTimeMillis();
            
            if (lastTime != null && (now - lastTime) < MIN_INTERVAL) {
                return false;
            }
            
            lastQuery.put(host, now);
            return true;
        }
    }
    
    /**
     * 验证 HTTPS 主机名
     */
    private static void verifyHostname(String expectedHost, 
                                      javax.net.ssl.HttpsURLConnection connection) 
            throws IOException {
        // 实际的主机名验证逻辑
        System.out.println("Verifying hostname: " + expectedHost);
    }
    
    /**
     * 检查是否为私有 IP
     */
    private static boolean isPrivateIp(String ip) {
        if (ip.equals("127.0.0.1")) return true;
        if (ip.startsWith("192.168.")) return true;
        if (ip.startsWith("10.")) return true;
        if (ip.startsWith("172.")) return true;
        if (ip.equals("169.254.169.254")) return true;
        return false;
    }
    
    public static void main(String[] args) {
        System.out.println("=== DNS Rebinding Attack Demo ===\n");
        
        System.out.println("=== Attack Scenario ===");
        System.out.println("1. Attacker controls attacker.com");
        System.out.println("2. First DNS query: attacker.com -> 1.2.3.4 (passes validation)");
        System.out.println("3. Second DNS query: attacker.com -> 192.168.1.100 (private IP)");
        System.out.println("4. Browser caches first result but makes request to second IP\n");
        
        System.out.println("=== Defense Mechanisms ===");
        System.out.println("1. Cache DNS results and reuse");
        System.out.println("2. Bind to resolved IP, not hostname");
        System.out.println("3. Rate limit DNS queries per host");
        System.out.println("4. Verify HTTPS certificate");
        System.out.println("5. Use separate DNS resolver");
    }
}
