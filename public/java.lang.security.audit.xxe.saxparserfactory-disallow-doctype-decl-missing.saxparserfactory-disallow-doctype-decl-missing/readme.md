##规则包名
java.lang.security.audit.xxe.saxparserfactory-disallow-doctype-decl-missing.saxparserfactory-disallow-doctype-decl-missing
##规则ID
saxparserfactory-disallow-doctype-decl-missing

# java.lang.security.audit.xxe.saxparserfactory-disallow-doctype-decl-missing.saxparserfactory-disallow-doctype-decl-missing

## 规则名称
java.lang.security.audit.xxe.saxparserfactory-disallow-doctype-decl-missing.saxparserfactory-disallow-doctype-decl-missing

## 规则描述
DOCTYPE declarations are enabled for this SAXParserFactory. This is vulnerable to XML external entity attacks. Disable this by setting the feature `http://apache.org/xml/features/disallow-doctype-decl` to true. Alternatively, allow DOCTYPE declarations and only prohibit external entities declarations. This can be done by setting the features `http://xml.org/sax/features/external-general-entities` and `http://xml.org/sax/features/external-parameter-entities` to false. NOTE - The previous links are not meant to be clicked. They are the literal config key values that are supposed to be used to disable these features. For more information, see https://semgrep.dev/docs/cheat-sheets/java-xxe/#3a-documentbuilderfactory.


### 规则描述中文版
该解析器启用了 DOCTYPE 声明，存在 XML 外部实体（XXE）风险。请将 disallow-doctype-decl 设为 true，或仅禁止外部实体声明。详见 Java XXE 防护文档。

### 关联CWE
- CWE-611: Improper Restriction of XML External Entity Reference
