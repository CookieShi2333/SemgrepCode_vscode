##规则包名
java.spring.security.injection.tainted-system-command.tainted-system-command
##规则ID
tainted-system-command

# java.spring.security.injection.tainted-system-command.tainted-system-command

## 规则名称
java.spring.security.injection.tainted-system-command.tainted-system-command

## 规则描述
Detected user input entering a method which executes a system command. This could result in a command injection vulnerability, which allows an attacker to inject an arbitrary system command onto the server. The attacker could download malware onto or steal data from the server. Instead, use ProcessBuilder, separating the command into individual arguments, like this: `new ProcessBuilder("ls", "-al", targetDirectory)`. Further, make sure you hardcode or allowlist the actual command so that attackers can't run arbitrary commands.


### 规则描述中文版
检测到用户输入进入执行系统命令的方法，可能导致命令注入。攻击者可执行任意系统命令。建议使用 ProcessBuilder 将命令与参数分离，并硬编码或白名单实际命令。

### 关联CWE
- CWE-78: Improper Neutralization of Special Elements used in an OS Command ('OS Command Injection')
