##规则包名
java.lang.security.audit.insecure-smtp-connection.insecure-smtp-connection
##规则ID
insecure-smtp-connection

# java.lang.security.audit.insecure-smtp-connection.insecure-smtp-connection

## 规则名称
java.lang.security.audit.insecure-smtp-connection.insecure-smtp-connection

## 规则描述
Insecure SMTP connection detected. This connection will trust any SSL certificate. Enable certificate verification by setting 'email.setSSLCheckServerIdentity(true)'.


### 规则描述中文版
检测到不安全的 SMTP 连接，将信任任意 SSL 证书。请通过 email.setSSLCheckServerIdentity(true) 等启用证书校验。

### 关联CWE
- CWE-297: Improper Validation of Certificate with Host Mismatch
