##规则包名
java.spring.security.spring-sqli-deepsemgrep.spring-sqli-deepsemgrep
##规则ID
spring-sqli-deepsemgrep

# java.spring.security.spring-sqli-deepsemgrep.spring-sqli-deepsemgrep

## 规则名称
java.spring.security.spring-sqli-deepsemgrep.spring-sqli-deepsemgrep

## 规则描述
Untrusted input might be used to build a database query, which can lead to a SQL injection vulnerability. An attacker can execute malicious SQL statements and gain unauthorized access to sensitive data, modify, delete data, or execute arbitrary system commands. To prevent this vulnerability, use prepared statements that do not concatenate user-controllable strings and use parameterized queries where SQL commands and user data are strictly separated. Also, consider using an object-relational (ORM) framework to operate with safer abstractions. To build SQL queries safely in Java, it is possible to adopt prepared statements by using the `java.sql.PreparedStatement` class with bind variables.


### 规则描述中文版
不可信输入可能被用于构建数据库查询，可能导致 SQL 注入。攻击者可执行恶意 SQL，未授权访问、修改或删除数据，甚至执行系统命令。防护建议：使用不拼接用户可控字符串的预编译语句与参数化查询，将 SQL 与数据严格分离；可考虑使用 ORM。在 Java 中可使用 java.sql.PreparedStatement 与绑定变量安全构建 SQL。

### 关联CWE
- CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')
