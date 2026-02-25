##规则包名
java.lang.security.audit.xss.no-direct-response-writer.no-direct-response-writer
##规则ID
no-direct-response-writer

# java.lang.security.audit.xss.no-direct-response-writer.no-direct-response-writer

## 规则名称
java.lang.security.audit.xss.no-direct-response-writer.no-direct-response-writer

## 规则描述
Detected a request with potential user-input going into a OutputStream or Writer object. This bypasses any view or template environments, including HTML escaping, which may expose this application to cross-site scripting (XSS) vulnerabilities. Consider using a view technology such as JavaServer Faces (JSFs) which automatically escapes HTML views.


### 规则描述中文版
检测到可能包含用户输入的请求数据进入 OutputStream 或 Writer，绕过视图与 HTML 转义，可能导致 XSS。建议使用 JSF 等视图技术自动转义。

### 关联CWE
- CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')
