package com.example.owasp.a10.ssrf;

/**
 * A10:2021 – Server-Side Request Forgery (SSRF)
 * 漏洞示例7：通过 IP 地址格式变换绕过检查
 * 攻击者可以使用不同的 IP 表示法来绕过白名单或黑名单检查
 */
public class SSRFViaIpBypass {
    
    /**
     * 漏洞代码：简单的 IP 检查可被绕过
     * 攻击示例：
     * 1. 127.1（简化的 localhost）
     * 2. 0x7f000001（十六进制）
     * 3. 2130706433（十进制）
     * 4. 127.0.0.1. （末尾点）
     */
    public static boolean isPrivateIpVulnerable(String ipOrHostname) {
        // 简单直接的字符串匹配 - 容易被绕过
        if (ipOrHostname.equals("localhost") || ipOrHostname.equals("127.0.0.1")) {
            return true;
        }
        
        if (ipOrHostname.startsWith("192.168.") ||
            ipOrHostname.startsWith("10.") ||
            ipOrHostname.startsWith("172.")) {
            return true;
        }
        
        // 无法检测到其他格式
        return false;
    }
    
    /**
     * 改进版本1：规范化 IP 地址后再检查
     */
    public static boolean isPrivateIpSecure(String hostOrIp) {
        try {
            // 将主机名/IP 解析为规范的 InetAddress
            java.net.InetAddress addr = java.net.InetAddress.getByName(hostOrIp);
            
            // 检查各种类型的私有/保留地址
            return addr.isLoopbackAddress() ||        // 127.0.0.1, ::1
                   addr.isLinkLocalAddress() ||       // 169.254.x.x
                   addr.isSiteLocalAddress() ||       // 10.x.x.x, 172.16-31.x.x, 192.168.x.x
                   addr.isAnyLocalAddress();          // 0.0.0.0, ::
                   
        } catch (Exception e) {
            // 如果无法解析，默认认为不可访问
            return true;
        }
    }
    
    /**
     * 攻击示例：各种 IP 格式变换
     */
    public static class IpFormatExamples {
        
        /**
         * 十六进制 IP 表示
         * 127.0.0.1 = 0x7f000001
         */
        public static String hexToIp(String hexIp) {
            try {
                // 0x7f000001 -> 127.0.0.1
                long ipLong = Long.parseLong(hexIp, 16);
                return String.format("%d.%d.%d.%d",
                        (ipLong >> 24) & 0xFF,
                        (ipLong >> 16) & 0xFF,
                        (ipLong >> 8) & 0xFF,
                        ipLong & 0xFF);
            } catch (Exception e) {
                return null;
            }
        }
        
        /**
         * 十进制 IP 表示
         * 127.0.0.1 = 2130706433
         */
        public static String decimalToIp(String decimalIp) {
            try {
                long ipLong = Long.parseLong(decimalIp);
                return String.format("%d.%d.%d.%d",
                        (ipLong >> 24) & 0xFF,
                        (ipLong >> 16) & 0xFF,
                        (ipLong >> 8) & 0xFF,
                        ipLong & 0xFF);
            } catch (Exception e) {
                return null;
            }
        }
        
        /**
         * 八进制 IP 表示
         * 127.0.0.1 = 0177.0.0.1
         */
        public static String octalToIp(String octalIp) {
            try {
                // 某些解析器支持八进制
                String[] parts = octalIp.split("\\.");
                StringBuilder result = new StringBuilder();
                
                for (int i = 0; i < parts.length; i++) {
                    if (i > 0) result.append(".");
                    result.append(Integer.parseInt(parts[i], 8));
                }
                return result.toString();
            } catch (Exception e) {
                return null;
            }
        }
        
        /**
         * 缩写 IP 表示
         * 127.1 可能被解析为 127.0.0.1
         */
        public static String abbreviatedToIp(String abbreviatedIp) {
            // 不同的系统可能有不同的处理方式
            // 例如：127.1 可能解析为 127.0.0.1
            try {
                java.net.InetAddress addr = java.net.InetAddress.getByName(abbreviatedIp);
                return addr.getHostAddress();
            } catch (Exception e) {
                return null;
            }
        }
        
        /**
         * IPv6 映射的 IPv4
         * ::ffff:127.0.0.1
         */
        public static String ipv6MappedIpv4(String ipv6) {
            try {
                java.net.InetAddress addr = java.net.InetAddress.getByName(ipv6);
                return addr.getHostAddress();
            } catch (Exception e) {
                return null;
            }
        }
    }
    
    /**
     * 改进版本2：严格的 IP 验证 - 使用 Pattern Matching
     */
    public static boolean isIpAddress(String ip) {
        // 使用正则表达式验证 IPv4 格式
        String ipv4Pattern = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}" +
                             "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
        
        return ip.matches(ipv4Pattern);
    }
    
    /**
     * 改进版本3：验证预期的 IP 格式
     */
    public static boolean validateIpFormat(String userIp) {
        try {
            // 只接受标准 IPv4 格式（4个十进制数）
            if (!isIpAddress(userIp)) {
                throw new IllegalArgumentException("Invalid IP format");
            }
            
            // 解析为 InetAddress 再转回字符串
            java.net.InetAddress addr = java.net.InetAddress.getByName(userIp);
            String canonicalIp = addr.getHostAddress();
            
            // 确保转换后的 IP 与原始输入相同
            if (!canonicalIp.equals(userIp)) {
                throw new SecurityException("IP format mismatch after normalization");
            }
            
            // 检查是否为私有地址
            if (isPrivateIpSecure(canonicalIp)) {
                throw new SecurityException("Private IP not allowed: " + canonicalIp);
            }
            
            return true;
            
        } catch (Exception e) {
            System.out.println("IP validation failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 改进版本4：逐字节验证 IP 地址
     */
    public static void validateIpAddressStrictly(String ipString) {
        try {
            // 拆分为四个部分
            String[] parts = ipString.split("\\.");
            
            if (parts.length != 4) {
                throw new SecurityException("Invalid IP format");
            }
            
            // 检查每个部分
            int[] octets = new int[4];
            for (int i = 0; i < 4; i++) {
                try {
                    octets[i] = Integer.parseInt(parts[i]);
                    
                    // 每个字节应该在 0-255 之间
                    if (octets[i] < 0 || octets[i] > 255) {
                        throw new SecurityException("Octet out of range: " + octets[i]);
                    }
                } catch (NumberFormatException e) {
                    throw new SecurityException("Invalid octet: " + parts[i]);
                }
            }
            
            // 检查私有地址范围
            if (octets[0] == 127) { // 127.0.0.0/8
                throw new SecurityException("Loopback address not allowed");
            }
            
            if (octets[0] == 10) { // 10.0.0.0/8
                throw new SecurityException("Private address not allowed");
            }
            
            if (octets[0] == 172 && octets[1] >= 16 && octets[1] <= 31) { // 172.16.0.0/12
                throw new SecurityException("Private address not allowed");
            }
            
            if (octets[0] == 192 && octets[1] == 168) { // 192.168.0.0/16
                throw new SecurityException("Private address not allowed");
            }
            
            System.out.println("IP validation passed: " + ipString);
            
        } catch (Exception e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== SSRF via IP Bypass Demo ===\n");
        
        System.out.println("=== IP Format Examples ===");
        System.out.println("Hex: 0x7f000001 = " + IpFormatExamples.hexToIp("7f000001"));
        System.out.println("Decimal: 2130706433 = " + IpFormatExamples.decimalToIp("2130706433"));
        System.out.println("Octal: 0177.0.0.1 = " + IpFormatExamples.octalToIp("0177.0.0.1"));
        System.out.println("Abbreviated: 127.1 = " + IpFormatExamples.abbreviatedToIp("127.1"));
        System.out.println("IPv6 mapped: ::ffff:127.0.0.1\n");
        
        System.out.println("=== Defense Mechanisms ===");
        System.out.println("1. Normalize IP to InetAddress");
        System.out.println("2. Validate strict IPv4 format");
        System.out.println("3. Check each octet (0-255)");
        System.out.println("4. Block all private IP ranges");
        System.out.println("5. Use allowlist instead of blocklist");
    }
}
