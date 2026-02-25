##规则包名
java.tars.jax-rs.xml.scala-xml.jax-rs-scala-xml-xml-injection-uri-params.jax-rs-scala-xml-xml-injection-uri-params
##规则ID
jax-rs-scala-xml-xml-injection-uri-params

# java.tars.jax-rs.xml.scala-xml.jax-rs-scala-xml-xml-injection-uri-params.jax-rs-scala-xml-xml-injection-uri-params

## 规则名称
java.tars.jax-rs.xml.scala-xml.jax-rs-scala-xml-xml-injection-uri-params.jax-rs-scala-xml-xml-injection-uri-params

## 规则描述
Untrusted input might be used to build an XML document, which can lead to a XML injection vulnerability. The manipulation of XML document can lead to doctypes being injected, which in turn can lead to information disclosure, denial of service, or RCE in rare cases.


### 规则描述中文版
不可信输入被用于构建 XML 文档，可能导致 XML 注入。篡改 XML 可能注入 DTD 等，进而导致信息泄露、拒绝服务或个别情况下的 RCE。

### 关联CWE
- CWE-91: XML Injection (aka Blind XPath Injection)
