##规则包名
java.java-jwt.security.audit.jwt-decode-without-verify.java-jwt-decode-without-verify
##规则ID
java-jwt-decode-without-verify

# java.java-jwt.security.audit.jwt-decode-without-verify.java-jwt-decode-without-verify

## 规则名称
java.java-jwt.security.audit.jwt-decode-without-verify.java-jwt-decode-without-verify

## 规则描述
Detected the decoding of a JWT token without a verify step. JWT tokens must be verified before use, otherwise the token's integrity is unknown. This means a malicious actor could forge a JWT token with any claims. Call '.verify()' before using the token.


### 规则描述中文版
检测到在未进行验证的情况下解码 JWT。JWT 在使用前必须验证，否则令牌完整性无法保证，攻击者可伪造任意声明的 JWT。请在使用前调用 .verify() 进行验证。

### 关联CWE
- CWE-345: Insufficient Verification of Data Authenticity
