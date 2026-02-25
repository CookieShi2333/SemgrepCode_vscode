##规则包名
java.lang.security.audit.crypto.use-of-default-aes.use-of-default-aes
##规则ID
use-of-default-aes

# java.lang.security.audit.crypto.use-of-default-aes.use-of-default-aes

## 规则名称
java.lang.security.audit.crypto.use-of-default-aes.use-of-default-aes

## 规则描述
Use of AES with no settings detected. By default, java.crypto.Cipher uses ECB mode. ECB doesn't  provide message confidentiality and is not semantically secure so should not be used. Instead, use a strong, secure cipher: java.crypto.Cipher.getInstance("AES/CBC/PKCS7PADDING"). See https://owasp.org/www-community/Using_the_Java_Cryptographic_Extensions for more information.


### 规则描述中文版
检测到使用弱加密算法、不安全的加密模式或配置（如弱密钥长度、ECB 模式、静态 IV、空加密等）。可能降低机密性与完整性保护。请改用强算法与安全配置（如 AES-GCM、正确 IV、RSA 带填充等）。详见加密最佳实践。

### 关联CWE
- CWE-327: Use of a Broken or Risky Cryptographic Algorithm
