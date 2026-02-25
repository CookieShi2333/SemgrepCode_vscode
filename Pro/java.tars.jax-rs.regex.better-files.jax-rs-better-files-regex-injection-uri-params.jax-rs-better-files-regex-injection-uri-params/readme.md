##规则包名
java.tars.jax-rs.regex.better-files.jax-rs-better-files-regex-injection-uri-params.jax-rs-better-files-regex-injection-uri-params
##规则ID
jax-rs-better-files-regex-injection-uri-params

# java.tars.jax-rs.regex.better-files.jax-rs-better-files-regex-injection-uri-params.jax-rs-better-files-regex-injection-uri-params

## 规则名称
java.tars.jax-rs.regex.better-files.jax-rs-better-files-regex-injection-uri-params.jax-rs-better-files-regex-injection-uri-params

## 规则描述
The regular expression identified appears vulnerable to Regular Expression Denial of Service (ReDoS) through catastrophic backtracking. If the input is attacker controllable, this vulnerability can lead to systems being non-responsive or may crash due to ReDoS. Where possible, re-write the regex so as not to leverage backtracking or use a library that offers default protection against ReDoS.


### 规则描述中文版
该正则可能因灾难性回溯导致正则拒绝服务（ReDoS）。若输入可由攻击者控制，系统可能无响应或崩溃。建议重写正则减少回溯或使用具备 ReDoS 防护的库。

### 关联CWE
- CWE-1333: Inefficient Regular Expression Complexity
