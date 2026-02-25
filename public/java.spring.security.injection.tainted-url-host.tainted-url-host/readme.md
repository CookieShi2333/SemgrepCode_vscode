##规则包名
java.spring.security.injection.tainted-url-host.tainted-url-host
##规则ID
tainted-url-host

# java.spring.security.injection.tainted-url-host.tainted-url-host

## 规则名称
java.spring.security.injection.tainted-url-host.tainted-url-host

## 规则描述
User data flows into the host portion of this manually-constructed URL. This could allow an attacker to send data to their own server, potentially exposing sensitive data such as cookies or authorization information sent with this request. They could also probe internal servers or other resources that the server running this code can access. (This is called server-side request forgery, or SSRF.) Do not allow arbitrary hosts. Instead, create an allowlist for approved hosts, hardcode the correct host, or ensure that the user data can only affect the path or parameters.


### 规则描述中文版
用户数据流入手动构造的 URL 的主机部分，可能导致服务端请求伪造（SSRF）。攻击者可窃取敏感数据或探测内网。建议禁止任意主机，使用白名单、硬编码正确主机或确保用户数据仅影响路径或参数。

### 关联CWE
- CWE-918: Server-Side Request Forgery (SSRF)
