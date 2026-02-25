##规则包名
java.lang.security.audit.cbc-padding-oracle.cbc-padding-oracle
##规则ID
cbc-padding-oracle

# java.lang.security.audit.cbc-padding-oracle.cbc-padding-oracle

## 规则名称
java.lang.security.audit.cbc-padding-oracle.cbc-padding-oracle

## 规则描述
Using CBC with PKCS5Padding is susceptible to padding oracle attacks. A malicious actor could discern the difference between plaintext with valid or invalid padding. Further, CBC mode does not include any integrity checks. Use 'AES/GCM/NoPadding' instead.


### 规则描述中文版
使用 CBC 与 PKCS5Padding 易受填充预言攻击。建议使用 AES-GCM 等认证加密，或采取防篡改与常量时间校验等措施。

### 关联CWE
- CWE-327: Use of a Broken or Risky Cryptographic Algorithm
