##规则包名
java.micronaut.audit.cookies.missing-secure.missing-secure
##规则ID
missing-secure

# java.micronaut.audit.cookies.missing-secure.missing-secure

## 规则名称
java.micronaut.audit.cookies.missing-secure.missing-secure

## 规则描述
Detected a cookie where the `Secure` flag is either missing or disabled. The `Secure` cookie flag instructs the browser to forbid sending the cookie over an insecure HTTP request. Set the `Secure` flag to `true` so the cookie will only be sent over HTTPS.


### 规则描述中文版
检测到未设置 'secure' 标志的 Cookie。secure 标志可防止通过 HTTP 等不安全通道传输 Cookie。请通过 $COOKIE.setSecure(true) 设置。

### 关联CWE
- CWE-614: Sensitive Cookie in HTTPS Session Without 'Secure' Attribute
