##规则包名
java.lang.security.audit.xxe.documentbuilderfactory-external-general-entities-true.documentbuilderfactory-external-general-entities-true
##规则ID
documentbuilderfactory-external-general-entities-true

# java.lang.security.audit.xxe.documentbuilderfactory-external-general-entities-true.documentbuilderfactory-external-general-entities-true

## 规则名称
java.lang.security.audit.xxe.documentbuilderfactory-external-general-entities-true.documentbuilderfactory-external-general-entities-true

## 规则描述
External entities are allowed for $DBFACTORY. This is vulnerable to XML external entity attacks. Disable this by setting the feature "http://xml.org/sax/features/external-general-entities" to false.


### 规则描述中文版
该解析器允许外部实体，存在 XXE 风险。请将对应 feature 设为 false 以禁用外部实体。

### 关联CWE
- CWE-611: Improper Restriction of XML External Entity Reference
