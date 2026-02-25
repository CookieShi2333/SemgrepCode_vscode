##规则包名
java.lang.security.audit.weak-ssl-context.weak-ssl-context
##规则ID
weak-ssl-context

# java.lang.security.audit.weak-ssl-context.weak-ssl-context

## 规则名称
java.lang.security.audit.weak-ssl-context.weak-ssl-context

## 规则描述
An insecure SSL context was detected. TLS versions 1.0, 1.1, and all SSL versions are considered weak encryption and are deprecated. Use SSLContext.getInstance("TLSv1.2") for the best security.


### 规则描述中文版
检测到不安全的 SSL 上下文。TLS 1.0/1.1 及所有 SSL 版本视为弱加密且已弃用。建议使用 SSLContext.getInstance("TLSv1.2") 等安全配置。

### 关联CWE
- CWE-326: Inadequate Encryption Strength
