package com.example.owasp.a06;

import java.util.ArrayList;
import java.util.List;

/**
 * A06:2021 – Vulnerable and Outdated Components
 * 漏洞演示：使用包含已知漏洞的旧版本依赖
 */
public class VulnerableComponents {
    
    /**
     * 演示：使用过时的 Apache Commons Collections（存在序列化漏洞）
     * 这个版本容易受到远程代码执行攻击
     */
    public static class VulnerableSerializer {
        
        /**
         * 直接反序列化不可信的数据 - 极其危险！
         */
        public static Object deserializeVulnerable(byte[] data) {
            try {
                java.io.ObjectInputStream ois = new java.io.ObjectInputStream(
                        new java.io.ByteArrayInputStream(data));
                return ois.readObject(); // 漏洞：没有验证对象类型
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    /**
     * 漏洞代码：使用过时的 XML 解析器（容易受到 XXE 攻击）
     */
    public static void parseXMLVulnerable(String xmlContent) {
        try {
            javax.xml.parsers.DocumentBuilderFactory factory = 
                    javax.xml.parsers.DocumentBuilderFactory.newInstance();
            
            // 没有禁用外部实体解析 - XXE 漏洞！
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            
            org.w3c.dom.Document doc = builder.parse(
                    new java.io.ByteArrayInputStream(xmlContent.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 改进版本：使用最新的库版本
     */
    public static class SecureDeserializer {
        
        /**
         * 安全的反序列化实现
         */
        public static Object deserializeSecure(byte[] data, Class<?> expectedClass) {
            try {
                ObjectInputStreamSecure ois = new ObjectInputStreamSecure(
                        new java.io.ByteArrayInputStream(data));
                ois.addAllowedClass(expectedClass);
                return ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    /**
     * 改进版本：安全的 XML 解析
     */
    public static void parseXMLSecure(String xmlContent) {
        try {
            javax.xml.parsers.DocumentBuilderFactory factory = 
                    javax.xml.parsers.DocumentBuilderFactory.newInstance();
            
            // 禁用外部实体引用
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(
                    new java.io.ByteArrayInputStream(xmlContent.getBytes()));
            
            System.out.println("XML parsed securely");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 模拟安全的对象输入流
     */
    static class ObjectInputStreamSecure extends java.io.ObjectInputStream {
        private List<Class<?>> allowedClasses = new ArrayList<>();
        
        ObjectInputStreamSecure(java.io.InputStream in) throws java.io.IOException {
            super(in);
        }
        
        void addAllowedClass(Class<?> clazz) {
            allowedClasses.add(clazz);
        }
        
        @Override
        protected Class<?> resolveClass(java.io.ObjectStreamClass desc) throws Exception {
            if (!allowedClasses.contains(desc.forClass())) {
                throw new java.io.InvalidClassException("Unauthorized class: " + desc.getName());
            }
            return super.resolveClass(desc);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Vulnerable and Outdated Components Demo ===");
        System.out.println("\nVulnerable Issues:");
        System.out.println("1. Outdated Apache Commons Collections - RCE vulnerability");
        System.out.println("2. Unpatched XML parser - XXE vulnerability");
        System.out.println("3. Old Log4j versions - RCE via log injection");
        
        System.out.println("\nSecure Practices:");
        System.out.println("1. Keep dependencies updated");
        System.out.println("2. Use dependency scanning tools (OWASP Dependency-Check)");
        System.out.println("3. Implement secure deserialization");
        System.out.println("4. Disable XML external entity processing");
    }
}
