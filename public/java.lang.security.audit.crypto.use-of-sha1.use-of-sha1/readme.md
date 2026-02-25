##规则包名
java.lang.security.audit.crypto.use-of-sha1.use-of-sha1
##规则ID
use-of-sha1

# java.lang.security.audit.crypto.use-of-sha1.use-of-sha1

## 规则名称
java.lang.security.audit.crypto.use-of-sha1.use-of-sha1

## 规则描述
Detected SHA1 hash algorithm which is considered insecure. SHA1 is not collision resistant and is therefore not suitable as a cryptographic signature. Instead, use PBKDF2 for password hashing or SHA256 or SHA512 for other hash function applications.


### 规则描述中文版
检测到使用弱加密算法、不安全的加密模式或配置（如弱密钥长度、ECB 模式、静态 IV、空加密等）。可能降低机密性与完整性保护。请改用强算法与安全配置（如 AES-GCM、正确 IV、RSA 带填充等）。详见加密最佳实践。

### 关联CWE
- CWE-328: Use of Weak Hash
