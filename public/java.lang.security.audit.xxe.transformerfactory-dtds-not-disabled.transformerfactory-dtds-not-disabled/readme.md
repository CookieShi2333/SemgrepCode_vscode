##规则包名
java.lang.security.audit.xxe.transformerfactory-dtds-not-disabled.transformerfactory-dtds-not-disabled
##规则ID
transformerfactory-dtds-not-disabled

# java.lang.security.audit.xxe.transformerfactory-dtds-not-disabled.transformerfactory-dtds-not-disabled

## 规则名称
java.lang.security.audit.xxe.transformerfactory-dtds-not-disabled.transformerfactory-dtds-not-disabled

## 规则描述
DOCTYPE declarations are enabled for this TransformerFactory. This is vulnerable to XML external entity attacks. Disable this by setting the attributes "accessExternalDTD" and "accessExternalStylesheet" to "".


### 规则描述中文版
该解析器启用了 DOCTYPE 声明，存在 XML 外部实体（XXE）风险。请将 disallow-doctype-decl 设为 true，或仅禁止外部实体声明。详见 Java XXE 防护文档。

### 关联CWE
- CWE-611: Improper Restriction of XML External Entity Reference
