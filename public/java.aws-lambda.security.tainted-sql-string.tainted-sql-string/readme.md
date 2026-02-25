##规则包名
java.aws-lambda.security.tainted-sql-string.tainted-sql-string
##规则ID
tainted-sql-string

# java.aws-lambda.security.tainted-sql-string.tainted-sql-string

## 规则名称
java.aws-lambda.security.tainted-sql-string.tainted-sql-string

## 规则描述
Detected user input used to manually construct a SQL string. This is usually bad practice because manual construction could accidentally result in a SQL injection. An attacker could use a SQL injection to steal or modify contents of the database. Instead, use a parameterized query which is available by default in most database engines. Alternatively, consider using an object-relational mapper (ORM) such as Sequelize which will protect your queries.


### 规则描述中文版
检测到用户输入被用于手动构造 SQL 字符串，可能引发 SQL 注入。请使用预编译语句或参数化查询。

### 关联CWE
- CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')
