##规则包名
java.servlets.security.servletresponse-writer-xss.servletresponse-writer-xss
##规则ID
servletresponse-writer-xss

# java.servlets.security.servletresponse-writer-xss.servletresponse-writer-xss

## 规则名称
java.servlets.security.servletresponse-writer-xss.servletresponse-writer-xss

## 规则描述
Untrusted user input enters a dangerous API, which can lead to a Cross-site scripting (XSS) vulnerability. XSS vulnerabilities occur when untrusted input executes malicious JavaScript code, leading to issues such as account compromise and sensitive information leakage. Ensure your data is properly encoded using org.owasp.encoder.Encode.forHtml: `Encode.forHtml($VAR)`.


### 规则描述中文版
不可信用户输入进入危险 API，可能导致跨站脚本（XSS）。未校验输入执行恶意 JavaScript 可能导致账号劫持与敏感信息泄露。请使用 org.owasp.encoder.Encode.forHtml 等对数据进行正确编码。

### 关联CWE
- CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')
