##规则包名
java.lang.security.audit.crypto.no-null-cipher.no-null-cipher
##规则ID
no-null-cipher

# java.lang.security.audit.crypto.no-null-cipher.no-null-cipher

## 规则名称
java.lang.security.audit.crypto.no-null-cipher.no-null-cipher

## 规则描述
NullCipher was detected. This will not encrypt anything; the cipher text will be the same as the plain text. Use a valid, secure cipher: Cipher.getInstance("AES/CBC/PKCS7PADDING"). See https://owasp.org/www-community/Using_the_Java_Cryptographic_Extensions for more information.


### 规则描述中文版
检测到使用弱加密算法、不安全的加密模式或配置（如弱密钥长度、ECB 模式、静态 IV、空加密等）。可能降低机密性与完整性保护。请改用强算法与安全配置（如 AES-GCM、正确 IV、RSA 带填充等）。详见加密最佳实践。

### 关联CWE
- CWE-327: Use of a Broken or Risky Cryptographic Algorithm
