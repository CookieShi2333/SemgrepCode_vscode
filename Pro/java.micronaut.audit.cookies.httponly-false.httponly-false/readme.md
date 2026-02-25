##规则包名
java.micronaut.audit.cookies.httponly-false.httponly-false
##规则ID
httponly-false

# java.micronaut.audit.cookies.httponly-false.httponly-false

## 规则名称
java.micronaut.audit.cookies.httponly-false.httponly-false

## 规则描述
Detected a cookie where the `HttpOnly` flag is either missing or disabled. The `HttpOnly` cookie flag instructs the browser to forbid client-side JavaScript to read the cookie. If JavaScript interaction is required, you can ignore this finding. However, set the `HttpOnly` flag to `true` in all other cases.


### 规则描述中文版
检测到 Cookie 的 HttpOnly 标志缺失或未启用。HttpOnly 可禁止浏览器端 JavaScript 读取该 Cookie。若非必须由脚本访问，请将 HttpOnly 设为 true。

### 关联CWE
- CWE-1004: Sensitive Cookie Without 'HttpOnly' Flag
