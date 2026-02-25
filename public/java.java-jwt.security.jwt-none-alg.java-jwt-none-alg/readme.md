##规则包名
java.java-jwt.security.jwt-none-alg.java-jwt-none-alg
##规则ID
java-jwt-none-alg

# java.java-jwt.security.jwt-none-alg.java-jwt-none-alg

## 规则名称
java.java-jwt.security.jwt-none-alg.java-jwt-none-alg

## 规则描述
Detected use of the 'none' algorithm in a JWT token. The 'none' algorithm assumes the integrity of the token has already been verified. This would allow a malicious actor to forge a JWT token that will automatically be verified. Do not explicitly use the 'none' algorithm. Instead, use an algorithm such as 'HS256'.


### 规则描述中文版
检测到 JWT 使用 "none" 或弱算法，攻击者可篡改令牌。应使用强签名算法并验证算法参数。

### 关联CWE
- CWE-327: Use of a Broken or Risky Cryptographic Algorithm
