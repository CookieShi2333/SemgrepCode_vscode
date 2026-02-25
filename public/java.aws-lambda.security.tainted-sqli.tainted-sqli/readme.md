##规则包名
java.aws-lambda.security.tainted-sqli.tainted-sqli
##规则ID
tainted-sqli

# java.aws-lambda.security.tainted-sqli.tainted-sqli

## 规则名称
java.aws-lambda.security.tainted-sqli.tainted-sqli

## 规则描述
Detected SQL statement that is tainted by `$EVENT` object. This could lead to SQL injection if variables in the SQL statement are not properly sanitized. Use parameterized SQL queries or properly sanitize user input instead.


### 规则描述中文版
检测到 SQL 语句受不可信数据（如事件对象）污染，可能引发 SQL 注入。请对输入进行校验并使用参数化查询。

### 关联CWE
- CWE-89: Improper Neutralization of Special Elements used in an SQL Command ('SQL Injection')
