##规则包名
java.lang.security.audit.crypto.rsa-no-padding.rsa-no-padding
##规则ID
rsa-no-padding

# java.lang.security.audit.crypto.rsa-no-padding.rsa-no-padding

## 规则名称
java.lang.security.audit.crypto.rsa-no-padding.rsa-no-padding

## 规则描述
Using RSA without OAEP mode weakens the encryption.


### 规则描述中文版
RSA 未使用 OAEP 模式会削弱加密安全性。请使用 OAEP 等安全填充模式。

### 关联CWE
- CWE-326: Inadequate Encryption Strength
