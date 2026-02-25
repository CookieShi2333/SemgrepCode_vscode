##规则包名
java.lang.security.audit.crypto.unencrypted-socket.unencrypted-socket
##规则ID
unencrypted-socket

# java.lang.security.audit.crypto.unencrypted-socket.unencrypted-socket

## 规则名称
java.lang.security.audit.crypto.unencrypted-socket.unencrypted-socket

## 规则描述
Detected use of a Java socket that is not encrypted. As a result, the traffic could be read by an attacker intercepting the network traffic. Use an SSLSocket created by 'SSLSocketFactory' or 'SSLServerSocketFactory' instead.


### 规则描述中文版
检测到使用未加密的 Java Socket，数据在网络上明文传输。建议使用 SSL/TLS 加密连接。

### 关联CWE
- CWE-319: Cleartext Transmission of Sensitive Information
