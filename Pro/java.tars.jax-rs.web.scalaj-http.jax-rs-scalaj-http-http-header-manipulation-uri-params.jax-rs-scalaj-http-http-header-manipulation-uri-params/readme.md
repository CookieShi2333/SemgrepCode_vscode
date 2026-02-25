##规则包名
java.tars.jax-rs.web.scalaj-http.jax-rs-scalaj-http-http-header-manipulation-uri-params.jax-rs-scalaj-http-http-header-manipulation-uri-params
##规则ID
jax-rs-scalaj-http-http-header-manipulation-uri-params

# java.tars.jax-rs.web.scalaj-http.jax-rs-scalaj-http-http-header-manipulation-uri-params.jax-rs-scalaj-http-http-header-manipulation-uri-params

## 规则名称
java.tars.jax-rs.web.scalaj-http.jax-rs-scalaj-http-http-header-manipulation-uri-params.jax-rs-scalaj-http-http-header-manipulation-uri-params

## 规则描述
Header values are supplied to sensitive string-based functionality. Processing a header from a request without sanitization or verification may allow a user to modify commands or functionality of the application.


### 规则描述中文版
请求头未经净化或验证即参与敏感字符串处理，可能允许用户篡改应用命令或行为。

### 关联CWE
- CWE-644: Improper Neutralization of HTTP Headers for Scripting Syntax
