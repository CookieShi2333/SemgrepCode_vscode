##规则包名
java.servlets.security.tainted-session-from-http-request-deepsemgrep.tainted-session-from-http-request-deepsemgrep
##规则ID
tainted-session-from-http-request-deepsemgrep

# java.servlets.security.tainted-session-from-http-request-deepsemgrep.tainted-session-from-http-request-deepsemgrep

## 规则名称
java.servlets.security.tainted-session-from-http-request-deepsemgrep.tainted-session-from-http-request-deepsemgrep

## 规则描述
Mixing trusted and untrusted data within the same structure can lead to trust boundary violations, where unvalidated data is mistakenly trusted, potentially bypassing security mechanisms. Thoroughly sanitize user input before passing it into such function calls.


### 规则描述中文版
不可信输入进入会话等敏感操作（如 setAttribute），会模糊信任边界，导致程序错误信任未校验数据。应对传入此类接口的用户输入进行充分净化。

### 关联CWE
- CWE-501: Trust Boundary Violation
