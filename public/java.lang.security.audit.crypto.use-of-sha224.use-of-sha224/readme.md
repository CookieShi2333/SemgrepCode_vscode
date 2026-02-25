##规则包名
java.lang.security.audit.crypto.use-of-sha224.use-of-sha224
##规则ID
use-of-sha224

# java.lang.security.audit.crypto.use-of-sha224.use-of-sha224

## 规则名称
java.lang.security.audit.crypto.use-of-sha224.use-of-sha224

## 规则描述
This code uses a 224-bit hash function, which is deprecated or disallowed in some security policies. Consider updating to a stronger hash function such as SHA-384 or higher to ensure compliance and security.


### 规则描述中文版
本规则已弃用。

### 关联CWE
- CWE-328: Use of Weak Hash
