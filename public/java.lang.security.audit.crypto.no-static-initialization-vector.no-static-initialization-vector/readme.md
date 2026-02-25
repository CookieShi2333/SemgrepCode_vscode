##规则包名
java.lang.security.audit.crypto.no-static-initialization-vector.no-static-initialization-vector
##规则ID
no-static-initialization-vector

# java.lang.security.audit.crypto.no-static-initialization-vector.no-static-initialization-vector

## 规则名称
java.lang.security.audit.crypto.no-static-initialization-vector.no-static-initialization-vector

## 规则描述
Initialization Vectors (IVs) for block ciphers should be randomly generated each time they are used. Using a static IV means the same plaintext encrypts to the same ciphertext every time, weakening the strength of the encryption.


### 规则描述中文版
分组密码的初始化向量（IV）应为随机或不可预测，且每次加密不同。使用静态 IV 会削弱加密安全性。

### 关联CWE
- CWE-329: Generation of Predictable IV with CBC Mode
