##规则包名
java.lang.security.audit.tainted-ldapi-from-http-request.tainted-ldapi-from-http-request
##规则ID
tainted-ldapi-from-http-request

# java.lang.security.audit.tainted-ldapi-from-http-request.tainted-ldapi-from-http-request

## 规则名称
java.lang.security.audit.tainted-ldapi-from-http-request.tainted-ldapi-from-http-request

## 规则描述
Detected input from a HTTPServletRequest going into an LDAP query. This could lead to LDAP injection if the input is not properly sanitized, which could result in attackers modifying objects in the LDAP tree structure. Ensure data passed to an LDAP query is not controllable or properly sanitize the data.


### 规则描述中文版
不可信输入（如来自 HTTPServletRequest）进入 LDAP 查询，若未正确净化可能导致 LDAP 注入，攻击者可修改 LDAP 树中的对象。请确保传入 LDAP 查询的数据不可被用户控制，或对数据进行正确净化。

### 关联CWE
- CWE-90: Improper Neutralization of Special Elements used in an LDAP Query ('LDAP Injection')
