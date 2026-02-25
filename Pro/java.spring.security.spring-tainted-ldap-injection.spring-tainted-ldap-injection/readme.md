##规则包名
java.spring.security.spring-tainted-ldap-injection.spring-tainted-ldap-injection
##规则ID
spring-tainted-ldap-injection

# java.spring.security.spring-tainted-ldap-injection.spring-tainted-ldap-injection

## 规则名称
java.spring.security.spring-tainted-ldap-injection.spring-tainted-ldap-injection

## 规则描述
Untrusted input might be used to build an LDAP query, which can allow attackers to run arbitrary LDAP queries. If an LDAP query must contain untrusted input then it must be escaped. Ensure data passed to an LDAP query is not controllable or properly sanitize the user input with functions like createEqualityFilter.


### 规则描述中文版
不可信输入可能被用于构建 LDAP 查询，使攻击者可执行任意 LDAP 查询。若查询中必须包含不可信输入，须进行转义；或使用 createEqualityFilter 等对输入进行净化。

### 关联CWE
- CWE-90: Improper Neutralization of Special Elements used in an LDAP Query ('LDAP Injection')
