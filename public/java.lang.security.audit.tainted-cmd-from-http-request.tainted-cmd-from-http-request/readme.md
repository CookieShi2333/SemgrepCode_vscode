##规则包名
java.lang.security.audit.tainted-cmd-from-http-request.tainted-cmd-from-http-request
##规则ID
tainted-cmd-from-http-request

# java.lang.security.audit.tainted-cmd-from-http-request.tainted-cmd-from-http-request

## 规则名称
java.lang.security.audit.tainted-cmd-from-http-request.tainted-cmd-from-http-request

## 规则描述
Detected input from a HTTPServletRequest going into a 'ProcessBuilder' or 'exec' command. This could lead to command injection if variables passed into the exec commands are not properly sanitized. Instead, avoid using these OS commands with user-supplied input, or, if you must use these commands, use a whitelist of specific values.


### 规则描述中文版
检测到 HTTPServletRequest 的输入进入 ProcessBuilder 或 exec，若未正确净化可能导致命令注入。请避免用用户输入执行 OS 命令，或使用白名单限制可执行命令。

### 关联CWE
- CWE-78: Improper Neutralization of Special Elements used in an OS Command ('OS Command Injection')
