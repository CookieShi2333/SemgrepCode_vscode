##规则包名
java.servlets.security.tainted-cmd-from-http-request-deepsemgrep.tainted-cmd-from-http-request-deepsemgrep
##规则ID
tainted-cmd-from-http-request-deepsemgrep

# java.servlets.security.tainted-cmd-from-http-request-deepsemgrep.tainted-cmd-from-http-request-deepsemgrep

## 规则名称
java.servlets.security.tainted-cmd-from-http-request-deepsemgrep.tainted-cmd-from-http-request-deepsemgrep

## 规则描述
Untrusted input might be injected into a command executed by the application, which can lead to a command injection vulnerability. An attacker can execute arbitrary commands, potentially gaining complete control of the system. To prevent this vulnerability, avoid executing OS commands with user input. If this is unavoidable, validate and sanitize the input, and use safe methods for executing the commands. For more information, see: [Java command injection prevention] (https://semgrep.dev/docs/cheat-sheets/java-command-injection/)


### 规则描述中文版
不可信输入可能被注入到应用执行的命令中，导致命令注入。攻击者可执行任意命令，进而完全控制系统。防护建议：避免使用用户输入执行 OS 命令；若不可避免，应对输入进行校验与净化，并采用安全的命令执行方式。详见 Java 命令注入防护备忘单。

### 关联CWE
- CWE-78: Improper Neutralization of Special Elements used in an OS Command ('OS Command Injection')
