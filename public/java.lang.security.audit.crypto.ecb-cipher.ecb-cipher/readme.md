##规则包名
java.lang.security.audit.crypto.ecb-cipher.ecb-cipher
##规则ID
ecb-cipher

# java.lang.security.audit.crypto.ecb-cipher.ecb-cipher

## 规则名称
java.lang.security.audit.crypto.ecb-cipher.ecb-cipher

## 规则描述
Cipher in ECB mode is detected. ECB mode produces the same output for the same input each time which allows an attacker to intercept and replay the data. Further, ECB mode does not provide any integrity checking. See https://find-sec-bugs.github.io/bugs.htm#CIPHER_INTEGRITY.


### 规则描述中文版
检测到使用弱加密算法、不安全的加密模式或配置（如弱密钥长度、ECB 模式、静态 IV、空加密等）。可能降低机密性与完整性保护。请改用强算法与安全配置（如 AES-GCM、正确 IV、RSA 带填充等）。详见加密最佳实践。

### 关联CWE
- CWE-327: Use of a Broken or Risky Cryptographic Algorithm
