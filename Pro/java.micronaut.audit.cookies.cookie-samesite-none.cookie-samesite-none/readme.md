##规则包名
java.micronaut.audit.cookies.cookie-samesite-none.cookie-samesite-none
##规则ID
cookie-samesite-none

# java.micronaut.audit.cookies.cookie-samesite-none.cookie-samesite-none

## 规则名称
java.micronaut.audit.cookies.cookie-samesite-none.cookie-samesite-none

## 规则描述
Detected a cookie options with the `SameSite` flag set to "None". This is a potential security risk that arises from the way web browsers manage cookies. In a typical web application, cookies are used to store and transmit session-related data between a client and a server. To enhance security, cookies can be marked with the "SameSite" attribute, which restricts their usage based on the origin of the page that set them. This attribute can have three values: "Strict," "Lax," or "None". Make sure the `SameSite` attribute of the important cookies (e.g., session cookie) is set to a reasonable value. When `SameSite` is set to "Strict", no 3rd party cookie will be sent with outgoing requests, this is the most secure and private setting but harder to deploy with good usability. Setting it to "Lax" is the minimum requirement.


### 规则描述中文版
检测到 Cookie 的 SameSite 被设为 "None"，存在安全风险。SameSite 可限制基于来源的 Cookie 使用。重要 Cookie（如会话）应设为合理值；"Strict" 最安全，"Lax" 为最低建议。

### 关联CWE
- CWE-1275: Sensitive Cookie with Improper SameSite Attribute
