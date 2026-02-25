##规则包名
java.lang.security.audit.unvalidated-redirect.unvalidated-redirect
##规则ID
unvalidated-redirect

# java.lang.security.audit.unvalidated-redirect.unvalidated-redirect

## 规则名称
java.lang.security.audit.unvalidated-redirect.unvalidated-redirect

## 规则描述
Application redirects to a destination URL specified by a user-supplied parameter that is not validated. This could direct users to malicious locations. Consider using an allowlist to validate URLs.


### 规则描述中文版
应用根据未校验的用户参数重定向到目标 URL，可能将用户导向恶意站点。建议使用白名单校验 URL。

### 关联CWE
- CWE-601: URL Redirection to Untrusted Site ('Open Redirect')
