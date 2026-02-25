##规则包名
java.lang.security.audit.formatted-sql-string.formatted-sql-string
##规则ID
formatted-sql-string

# java.lang.security.audit.formatted-sql-string.formatted-sql-string

## 规则名称
java.lang.security.audit.formatted-sql-string.formatted-sql-string

## 规则描述
Detected a formatted string in a SQL statement. This could lead to SQL injection if variables in the SQL statement are not properly sanitized. Use a prepared statements (java.sql.PreparedStatement) instead. You can obtain a PreparedStatement using 'connection.prepareStatement'.


### 规则描述中文版
检测到 SQL 语句中使用格式化字符串，可能引入 SQL 注入。请使用参数化查询或预编译语句。

### 关联CWE
- CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')
