##规则包名
java.spring.security.audit.spring-sqli.spring-sqli
##规则ID
spring-sqli

# java.spring.security.audit.spring-sqli.spring-sqli

## 规则名称
java.spring.security.audit.spring-sqli.spring-sqli

## 规则描述
Detected a string argument from a public method contract in a raw SQL statement. This could lead to SQL injection if variables in the SQL statement are not properly sanitized. Use a prepared statements (java.sql.PreparedStatement) instead. You can obtain a PreparedStatement using 'connection.prepareStatement'.


### 规则描述中文版
检测到来自公共方法契约的字符串参数进入原始 SQL，若未正确净化可能导致 SQL 注入。请使用 java.sql.PreparedStatement 与 connection.prepareStatement。

### 关联CWE
- CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')
