##规则包名
java.micronaut.redirect.open-redirect.open-redirect
##规则ID
open-redirect

# java.micronaut.redirect.open-redirect.open-redirect

## 规则名称
java.micronaut.redirect.open-redirect.open-redirect

## 规则描述
The application builds a URL using user-controlled input which can lead to an open redirect vulnerability. An attacker can manipulate the URL and redirect users to an arbitrary domain. Open redirect vulnerabilities can lead to issues such as Cross-site scripting (XSS) or redirecting to a malicious domain for activities such as phishing to capture users' credentials. To prevent this vulnerability perform strict input validation of the domain against an allowlist of approved domains. Notify a user in your application that they are leaving the website. Display a domain where they are redirected to the user. A user can then either accept or deny the redirect to an untrusted site.


### 规则描述中文版
应用使用用户可控输入构建 URL，可能导致开放重定向。攻击者可操纵 URL 将用户重定向到任意域名，进而用于钓鱼或窃取凭据。防护建议：对域名做严格白名单校验、在应用中提示用户即将离开本站并显示目标域名，由用户选择是否接受重定向。

### 关联CWE
- CWE-601: URL Redirection to Untrusted Site ('Open Redirect')
