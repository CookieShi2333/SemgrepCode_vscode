##规则包名
java.servlets.security.tainted-xml-decoder.tainted-xml-decoder
##规则ID
tainted-xml-decoder

# java.servlets.security.tainted-xml-decoder.tainted-xml-decoder

## 规则名称
java.servlets.security.tainted-xml-decoder.tainted-xml-decoder

## 规则描述
The application is using an XML parser that has not been safely configured. This might lead to XML External Entity (XXE) vulnerabilities when parsing user-controlled input. An attacker can include document type definitions (DTDs) which can interact with internal or external hosts. XXE can lead to other vulnerabilities, such as Local File Inclusion (LFI), Remote Code Execution (RCE), and Server- side request forgery (SSRF), depending on the application configuration. An attacker can also use DTDs to expand recursively, leading to a Denial-of-Service (DoS) attack, also known as a Billion Laughs Attack. The best defense against XXE is to have an XML parser that supports disabling DTDs. Limiting the use of external entities from the start can prevent the parser from being used to process untrusted XML files. Reducing dependencies on external resources is also a good practice for performance reasons. It is difficult to guarantee that even a trusted XML file on your server or during transmission has not been tampered with by a malicious third-party. For more information, see: [Java XXE prevention](https://semgrep.dev/docs/cheat-sheets/java-xxe/)


### 规则描述中文版
应用使用了未安全配置的 XML 解析器，在解析用户可控输入时可能导致 XML 外部实体（XXE）漏洞。攻击者可通过 DTD 等与内部或外部主机交互，进而造成本地文件包含（LFI）、远程代码执行（RCE）、服务端请求伪造（SSRF），或通过递归展开 DTD 导致拒绝服务（如 Billion Laughs）。建议禁用 DTD 或按官方文档（如 Java XXE 防护备忘单）配置解析器：如 setFeature(disallow-doctype-decl, true)、ACCESS_EXTERNAL_DTD/SCHEMA 置空、关闭外部实体等。

### 关联CWE
- CWE-611: Improper Restriction of XML External Entity Reference
