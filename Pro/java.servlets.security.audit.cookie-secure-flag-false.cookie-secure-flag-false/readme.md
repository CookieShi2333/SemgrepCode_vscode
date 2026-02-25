##规则包名
java.servlets.security.audit.cookie-secure-flag-false.cookie-secure-flag-false
##规则ID
cookie-secure-flag-false

# java.servlets.security.audit.cookie-secure-flag-false.cookie-secure-flag-false

## 规则名称
java.servlets.security.audit.cookie-secure-flag-false.cookie-secure-flag-false

## 规则描述
A cookie was detected without setting the 'secure' flag. The 'secure' flag for cookies prevents the client from transmitting the cookie over insecure channels such as HTTP. Set the 'secure' flag by calling '$COOKIE.setSecure(true);'


### 规则描述中文版
检测到未设置 'secure' 标志的 Cookie。secure 标志可防止通过 HTTP 等不安全通道传输 Cookie。请通过 $COOKIE.setSecure(true) 设置。

### 关联CWE
- CWE-614: Sensitive Cookie in HTTPS Session Without 'Secure' Attribute
