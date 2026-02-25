##规则包名
java.lang.security.audit.tainted-session-from-http-request.tainted-session-from-http-request
##规则ID
tainted-session-from-http-request

# java.lang.security.audit.tainted-session-from-http-request.tainted-session-from-http-request

## 规则名称
java.lang.security.audit.tainted-session-from-http-request.tainted-session-from-http-request

## 规则描述
Detected input from a HTTPServletRequest going into a session command, like `setAttribute`. User input into such a command could lead to an attacker inputting malicious code into your session parameters, blurring the line between what's trusted and untrusted, and therefore leading to a trust boundary violation. This could lead to programmers trusting unvalidated data. Instead, thoroughly sanitize user input before passing it into such function calls.


### 规则描述中文版
不可信输入进入会话等敏感操作（如 setAttribute），会模糊信任边界，导致程序错误信任未校验数据。应对传入此类接口的用户输入进行充分净化。

### 关联CWE
- CWE-501: Trust Boundary Violation
