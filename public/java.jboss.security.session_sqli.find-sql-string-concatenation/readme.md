##规则包名
java.jboss.security.session_sqli.find-sql-string-concatenation
##规则ID
find-sql-string-concatenation

# java.jboss.security.session_sqli.find-sql-string-concatenation

## 规则名称
java.jboss.security.session_sqli.find-sql-string-concatenation

## 规则描述
In $METHOD, $X is used to construct a SQL query via string concatenation.


### 规则描述中文版
检测到通过字符串拼接构造 SQL 查询，若变量未正确净化可能导致 SQL 注入。请使用 java.sql.PreparedStatement 或 connection.prepareStatement。

### 关联CWE
- CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')
