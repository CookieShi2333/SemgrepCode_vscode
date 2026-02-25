##规则包名
java.lang.security.audit.http-response-splitting.http-response-splitting
##规则ID
http-response-splitting

# java.lang.security.audit.http-response-splitting.http-response-splitting

## 规则名称
java.lang.security.audit.http-response-splitting.http-response-splitting

## 规则描述
Older Java application servers are vulnerable to HTTP response splitting, which may occur if an HTTP request can be injected with CRLF characters. This finding is reported for completeness; it is recommended to ensure your environment is not affected by testing this yourself.


### 规则描述中文版
未校验的输入进入 HTTP 响应头可能导致 HTTP 响应拆分，攻击者可注入额外头或正文。请对写入头或状态行的输入进行校验与编码。

### 关联CWE
- CWE-113: Improper Neutralization of CRLF Sequences in HTTP Headers ('HTTP Request/Response Splitting')
