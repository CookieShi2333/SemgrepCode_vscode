##规则包名
java.tars.micronaut.web.jax-rs.micronaut-jax-rs-xss-uri-params.micronaut-jax-rs-xss-uri-params
##规则ID
micronaut-jax-rs-xss-uri-params

# java.tars.micronaut.web.jax-rs.micronaut-jax-rs-xss-uri-params.micronaut-jax-rs-xss-uri-params

## 规则名称
java.tars.micronaut.web.jax-rs.micronaut-jax-rs-xss-uri-params.micronaut-jax-rs-xss-uri-params

## 规则描述
Untrusted input could be used to tamper with a web page rendering, which can lead to a Cross-site scripting (XSS) vulnerability. XSS vulnerabilities occur when untrusted input executes malicious JavaScript code, leading to issues such as account compromise and sensitive information leakage. To prevent this vulnerability, validate the user input, perform contextual output encoding or sanitize the input.


### 规则描述中文版
不可信输入可能被用于篡改网页渲染，导致跨站脚本（XSS）。未校验的输入执行恶意 JavaScript 可能导致账号劫持、敏感信息泄露等。防护建议：校验用户输入、按上下文进行输出编码或对输入进行净化。

### 关联CWE
- CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')
