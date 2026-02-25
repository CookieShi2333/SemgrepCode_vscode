##规则包名
java.servlets.security.crlf-injection-logs.crlf-injection-logs
##规则ID
crlf-injection-logs

# java.servlets.security.crlf-injection-logs.crlf-injection-logs

## 规则名称
java.servlets.security.crlf-injection-logs.crlf-injection-logs

## 规则描述
When data from an untrusted source is put into a logger and not neutralized correctly, an attacker could forge log entries or include malicious content.


### 规则描述中文版
来自不可信源的数据在未正确净化的情况下写入日志，攻击者可伪造日志条目或注入恶意内容。

### 关联CWE
- CWE-93: Improper Neutralization of CRLF Sequences ('CRLF Injection')
