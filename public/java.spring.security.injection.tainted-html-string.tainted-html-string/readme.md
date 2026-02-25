##规则包名
java.spring.security.injection.tainted-html-string.tainted-html-string
##规则ID
tainted-html-string

# java.spring.security.injection.tainted-html-string.tainted-html-string

## 规则名称
java.spring.security.injection.tainted-html-string.tainted-html-string

## 规则描述
Detected user input flowing into a manually constructed HTML string. You may be accidentally bypassing secure methods of rendering HTML by manually constructing HTML and this could create a cross-site scripting vulnerability, which could let attackers steal sensitive user data. To be sure this is safe, check that the HTML is rendered safely. You can use the OWASP ESAPI encoder if you must render user data.


### 规则描述中文版
检测到用户输入流入手动构造的 HTML 字符串，可能绕过安全渲染并导致跨站脚本（XSS）。请确保 HTML 安全渲染，必要时使用 OWASP ESAPI 等编码器。

### 关联CWE
- CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')
