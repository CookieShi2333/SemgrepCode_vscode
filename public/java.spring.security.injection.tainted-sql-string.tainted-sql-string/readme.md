##规则包名
java.spring.security.injection.tainted-sql-string.tainted-sql-string
##规则ID
tainted-sql-string

# java.spring.security.injection.tainted-sql-string.tainted-sql-string

## 规则名称
java.spring.security.injection.tainted-sql-string.tainted-sql-string

## 规则描述
User data flows into this manually-constructed SQL string. User data can be safely inserted into SQL strings using prepared statements or an object-relational mapper (ORM). Manually-constructed SQL strings is a possible indicator of SQL injection, which could let an attacker steal or manipulate data from the database. Instead, use prepared statements (`connection.PreparedStatement`) or a safe library.


### 规则描述中文版
用户数据流入手动构造的 SQL 字符串，可能引发 SQL 注入。请使用预编译语句（PreparedStatement）或 ORM 安全插入用户数据。

### 关联CWE
- CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')
