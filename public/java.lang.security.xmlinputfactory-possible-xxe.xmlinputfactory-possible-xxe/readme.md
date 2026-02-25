##规则包名
java.lang.security.xmlinputfactory-possible-xxe.xmlinputfactory-possible-xxe
##规则ID
xmlinputfactory-possible-xxe

# java.lang.security.xmlinputfactory-possible-xxe.xmlinputfactory-possible-xxe

## 规则名称
java.lang.security.xmlinputfactory-possible-xxe.xmlinputfactory-possible-xxe

## 规则描述
XML external entities are not explicitly disabled for this XMLInputFactory. This could be vulnerable to XML external entity vulnerabilities. Explicitly disable external entities by setting "javax.xml.stream.isSupportingExternalEntities" to false.


### 规则描述中文版
该 XMLInputFactory 未显式禁用外部实体，可能存在 XXE 漏洞。请将 javax.xml.stream.isSupportingExternalEntities 设为 false。

### 关联CWE
- CWE-611: Improper Restriction of XML External Entity Reference
