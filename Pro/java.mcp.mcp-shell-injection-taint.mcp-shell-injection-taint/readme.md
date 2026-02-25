##规则包名
java.mcp.mcp-shell-injection-taint.mcp-shell-injection-taint
##规则ID
mcp-shell-injection-taint

# java.mcp.mcp-shell-injection-taint.mcp-shell-injection-taint

## 规则名称
java.mcp.mcp-shell-injection-taint.mcp-shell-injection-taint

## 规则描述
Detected MCP server parameter data flowing into a system command execution function.
This could result in a command injection vulnerability if an attacker can control
the HTTP request parameters, MCP tool arguments, or configuration values. MCP servers
handle external requests and should treat all parameter data as untrusted input.
An attacker could inject malicious commands to execute arbitrary code on the server.

To fix this:
1. Avoid using Runtime.exec() with concatenated strings
2. Use ProcessBuilder with individual arguments instead of shell commands
3. Validate and sanitize all input parameters
4. Use allowlisting to restrict allowed commands and arguments
5. Consider using Java APIs instead of system commands when possible
6. If shell commands are necessary, use proper escaping and validation



### 规则描述中文版
检测到 MCP 服务参数数据流入系统命令执行函数，存在命令注入等风险。

### 关联CWE
- CWE-78: Improper Neutralization of Special Elements used in an OS Command ('OS Command Injection')
