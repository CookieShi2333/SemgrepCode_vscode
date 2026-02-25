##规则包名
java.lang.security.audit.blowfish-insufficient-key-size.blowfish-insufficient-key-size
##规则ID
blowfish-insufficient-key-size

# java.lang.security.audit.blowfish-insufficient-key-size.blowfish-insufficient-key-size

## 规则名称
java.lang.security.audit.blowfish-insufficient-key-size.blowfish-insufficient-key-size

## 规则描述
Using less than 128 bits for Blowfish is considered insecure. Use 128 bits or more, or switch to use AES instead.


### 规则描述中文版
检测到使用弱加密算法、不安全的加密模式或配置（如弱密钥长度、ECB 模式、静态 IV、空加密等）。可能降低机密性与完整性保护。请改用强算法与安全配置（如 AES-GCM、正确 IV、RSA 带填充等）。详见加密最佳实践。

### 关联CWE
- CWE-326: Inadequate Encryption Strength
