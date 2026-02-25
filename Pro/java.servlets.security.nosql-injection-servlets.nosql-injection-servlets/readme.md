##规则包名
java.servlets.security.nosql-injection-servlets.nosql-injection-servlets
##规则ID
nosql-injection-servlets

# java.servlets.security.nosql-injection-servlets.nosql-injection-servlets

## 规则名称
java.servlets.security.nosql-injection-servlets.nosql-injection-servlets

## 规则描述
Untrusted input might be used to build a database query, which can lead to a NoSQL injection vulnerability. An attacker can execute malicious NoSQL statements and gain unauthorized access to sensitive data, modify, delete data, or execute arbitrary system commands. Make sure all user input is validated and sanitized, and avoid if possible to use it to construct the NoSQL statement.


### 规则描述中文版
不可信输入可能被用于构建数据库查询，可能导致 SQL 注入。攻击者可执行恶意 SQL，未授权访问、修改或删除数据，甚至执行系统命令。防护建议：使用不拼接用户可控字符串的预编译语句与参数化查询，将 SQL 与数据严格分离；可考虑使用 ORM。在 Java 中可使用 java.sql.PreparedStatement 与绑定变量安全构建 SQL。
