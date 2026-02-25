##规则包名
java.lang.security.audit.md5-used-as-password.md5-used-as-password
##规则ID
md5-used-as-password

# java.lang.security.audit.md5-used-as-password.md5-used-as-password

## 规则名称
java.lang.security.audit.md5-used-as-password.md5-used-as-password

## 规则描述
It looks like MD5 is used as a password hash. MD5 is not considered a secure password hash because it can be cracked by an attacker in a short amount of time. Use a suitable password hashing function such as PBKDF2 or bcrypt. You can use `javax.crypto.SecretKeyFactory` with `SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")` or, if using Spring, `org.springframework.security.crypto.bcrypt`.


### 规则描述中文版
检测到使用 MD5 作为密码哈希。MD5 不适合作为密码哈希，易被快速破解。请使用 PBKDF2、bcrypt 等（如 SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1") 或 Spring 的 bcrypt）。

### 关联CWE
- CWE-327: Use of a Broken or Risky Cryptographic Algorithm
