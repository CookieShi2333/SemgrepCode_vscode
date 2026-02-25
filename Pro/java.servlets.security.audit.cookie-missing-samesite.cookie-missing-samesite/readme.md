##规则包名
java.servlets.security.audit.cookie-missing-samesite.cookie-missing-samesite
##规则ID
cookie-missing-samesite

# java.servlets.security.audit.cookie-missing-samesite.cookie-missing-samesite

## 规则名称
java.servlets.security.audit.cookie-missing-samesite.cookie-missing-samesite

## 规则描述
The application does not appear to verify inbound requests which can lead to a Cross-site request forgery (CSRF) vulnerability. If the application uses cookie-based authentication, an attacker can trick users into sending authenticated HTTP requests without their knowledge from any arbitrary domain they visit. To prevent this vulnerability start by identifying if the framework or library leveraged has built-in features or offers plugins for CSRF protection. CSRF tokens should be unique and securely random. The `Synchronizer Token` or `Double Submit Cookie` patterns with defense-in- depth mechanisms such as the `sameSite` cookie flag can help prevent CSRF. For more information, see: [Cross-site request forgery prevention](https://cheatsheetseries.owasp.org/cheatsheets/Cross- Site_Request_Forgery_Prevention_Cheat_Sheet.html)


### 规则描述中文版
应用未对入站请求进行验证，可能导致跨站请求伪造（CSRF）。在使用基于 Cookie 的认证时，攻击者可诱使用户从任意站点发送已认证请求。建议使用框架自带的 CSRF 防护或插件，使用唯一且安全的随机 CSRF 令牌，并配合 SameSite 等机制。详见 OWASP CSRF 防护备忘单。

### 关联CWE
- CWE-352: Cross-Site Request Forgery (CSRF)
