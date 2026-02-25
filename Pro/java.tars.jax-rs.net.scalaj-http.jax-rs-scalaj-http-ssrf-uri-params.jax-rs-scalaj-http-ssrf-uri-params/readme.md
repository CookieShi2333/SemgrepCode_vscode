##规则包名
java.tars.jax-rs.net.scalaj-http.jax-rs-scalaj-http-ssrf-uri-params.jax-rs-scalaj-http-ssrf-uri-params
##规则ID
jax-rs-scalaj-http-ssrf-uri-params

# java.tars.jax-rs.net.scalaj-http.jax-rs-scalaj-http-ssrf-uri-params.jax-rs-scalaj-http-ssrf-uri-params

## 规则名称
java.tars.jax-rs.net.scalaj-http.jax-rs-scalaj-http-ssrf-uri-params.jax-rs-scalaj-http-ssrf-uri-params

## 规则描述
Untrusted input might be used to build an HTTP request, which can lead to a Server-side request forgery (SSRF) vulnerability. SSRF allows an attacker to send crafted requests from the server side to other internal or external systems. SSRF can lead to unauthorized access to sensitive data and, in some cases, allow the attacker to control applications or systems that trust the vulnerable service. To prevent this vulnerability, avoid allowing user input to craft the base request. Instead, treat it as part of the path or query parameter and encode it appropriately. When user input is necessary to prepare the HTTP request, perform strict input validation. Additionally, whenever possible, use allowlists to only interact with expected, trusted domains.


### 规则描述中文版
不可信输入可能被用于构建 HTTP 请求，可能导致服务端请求伪造（SSRF）。攻击者可利用服务端向其他内部或外部系统发送构造请求，从而未授权访问敏感数据甚至控制应用。防护建议：勿用用户输入构造请求基础部分；将用户输入仅作为路径或查询参数并正确编码；必要时对输入做严格校验，并尽量使用白名单仅访问受信任域。

### 关联CWE
- CWE-918: Server-Side Request Forgery (SSRF)
