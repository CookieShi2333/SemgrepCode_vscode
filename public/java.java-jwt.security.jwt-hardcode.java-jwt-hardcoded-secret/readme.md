##规则包名
java.java-jwt.security.jwt-hardcode.java-jwt-hardcoded-secret
##规则ID
java-jwt-hardcoded-secret

# java.java-jwt.security.jwt-hardcode.java-jwt-hardcoded-secret

## 规则名称
java.java-jwt.security.jwt-hardcode.java-jwt-hardcoded-secret

## 规则描述
A hard-coded credential was detected. It is not recommended to store credentials in source-code, as this risks secrets being leaked and used by either an internal or external malicious adversary. It is recommended to use environment variables to securely provide credentials or retrieve credentials from a secure vault or HSM (Hardware Security Module).


### 规则描述中文版
检测到硬编码的凭据。凭据不应写在源码中，应使用环境变量或安全存储。

### 关联CWE
- CWE-798: Use of Hard-coded Credentials
