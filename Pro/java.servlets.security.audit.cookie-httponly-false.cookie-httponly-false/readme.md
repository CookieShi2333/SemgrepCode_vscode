##规则包名
java.servlets.security.audit.cookie-httponly-false.cookie-httponly-false
##规则ID
cookie-httponly-false

# java.servlets.security.audit.cookie-httponly-false.cookie-httponly-false

## 规则名称
java.servlets.security.audit.cookie-httponly-false.cookie-httponly-false

## 规则描述
A cookie was detected without setting the 'HttpOnly' flag. The 'HttpOnly' flag for cookies instructs the browser to forbid client-side scripts from reading the cookie. Set the 'HttpOnly' flag by calling 'cookie.setHttpOnly(true);'


### 规则描述中文版
检测到未设置 'HttpOnly' 标志的 Cookie。HttpOnly 可禁止客户端脚本读取该 Cookie。请通过 cookie.setHttpOnly(true) 设置。

### 关联CWE
- CWE-1004: Sensitive Cookie Without 'HttpOnly' Flag
