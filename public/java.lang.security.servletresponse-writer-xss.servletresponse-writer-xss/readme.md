##规则包名
java.lang.security.servletresponse-writer-xss.servletresponse-writer-xss
##规则ID
servletresponse-writer-xss

# java.lang.security.servletresponse-writer-xss.servletresponse-writer-xss

## 规则名称
java.lang.security.servletresponse-writer-xss.servletresponse-writer-xss

## 规则描述
Cross-site scripting detected in HttpServletResponse writer with variable '$VAR'. User input was detected going directly from the HttpServletRequest into output. Ensure your data is properly encoded using org.owasp.encoder.Encode.forHtml: 'Encode.forHtml($VAR)'.


### 规则描述中文版
检测到 HttpServletResponse 写入器中的跨站脚本风险，用户输入直接从 HttpServletRequest 进入输出。请使用 Encode.forHtml 等对数据进行正确编码。

### 关联CWE
- CWE-79: Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')
