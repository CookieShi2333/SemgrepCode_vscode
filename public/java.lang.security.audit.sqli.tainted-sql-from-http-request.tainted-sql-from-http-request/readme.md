##规则包名
java.lang.security.audit.sqli.tainted-sql-from-http-request.tainted-sql-from-http-request
##规则ID
tainted-sql-from-http-request

# java.lang.security.audit.sqli.tainted-sql-from-http-request.tainted-sql-from-http-request

## 规则名称
java.lang.security.audit.sqli.tainted-sql-from-http-request.tainted-sql-from-http-request

## 规则描述
Detected input from a HTTPServletRequest going into a SQL sink or statement. This could lead to SQL injection if variables in the SQL statement are not properly sanitized. Use parameterized SQL queries or properly sanitize user input instead.


### 规则描述中文版
检测到 HTTPServletRequest 的输入进入 SQL 接收点或语句，若未正确净化可能导致 SQL 注入。请使用参数化查询或对用户输入进行净化。

### 关联CWE
- CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')
