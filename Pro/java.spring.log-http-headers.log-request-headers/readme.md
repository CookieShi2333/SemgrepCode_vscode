##规则包名
java.spring.log-http-headers.log-request-headers
##规则ID
log-request-headers

# java.spring.log-http-headers.log-request-headers

## 规则名称
java.spring.log-http-headers.log-request-headers

## 规则描述
The application stores potentially sensitive information in log files. This could lead to a vulnerability, if an attacker can gain access to logs and then use the sensitive information to perform further attacks. When dealing with HTTP requests, sensitive data could be, for instance, JWT tokens or other session identifiers. To prevent this vulnerability review the type of information being logged. Sensitive information can be identified and filtered or obfuscated before calling logging functions.


### 规则描述中文版
应用将潜在敏感信息写入日志，攻击者获取日志后可能利用这些信息进行进一步攻击。应对写入日志的信息类型进行审查，在调用日志接口前识别、过滤或脱敏敏感信息。

### 关联CWE
- CWE-532: Insertion of Sensitive Information into Log File
