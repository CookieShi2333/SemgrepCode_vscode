##规则包名
java.lang.security.net.http.urlconnection-hardcoded-secret-in-request-header.urlconnection-hardcoded-secret-in-request-header
##规则ID
urlconnection-hardcoded-secret-in-request-header

# java.lang.security.net.http.urlconnection-hardcoded-secret-in-request-header.urlconnection-hardcoded-secret-in-request-header

## 规则名称
java.lang.security.net.http.urlconnection-hardcoded-secret-in-request-header.urlconnection-hardcoded-secret-in-request-header

## 规则描述
A secret is hard-coded in the application. Secrets stored in source code, such as credentials, identifiers, and other types of sensitive data, can be leaked and used by internal or external malicious actors. Use environment variables to securely provide credentials and other secrets or retrieve them from a secure vault or Hardware Security Module (HSM).


### 规则描述中文版
应用程序中存在硬编码的密钥。存储在源代码中的敏感信息（如凭据、标识符及其他类型的敏感数据）可能被泄露，并被内部或外部恶意攻击者利用。应使用环境变量安全地提供凭据及其他密钥，或从安全保险库或硬件安全模块（HSM）中获取。

### 关联CWE
- CWE-798: Use of Hard-coded Credentials
