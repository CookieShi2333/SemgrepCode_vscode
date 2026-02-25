##规则包名
java.lang.security.audit.tainted-env-from-http-request.tainted-env-from-http-request
##规则ID
tainted-env-from-http-request

# java.lang.security.audit.tainted-env-from-http-request.tainted-env-from-http-request

## 规则名称
java.lang.security.audit.tainted-env-from-http-request.tainted-env-from-http-request

## 规则描述
Detected input from a HTTPServletRequest going into the environment variables of an 'exec' command.  Instead, call the command with user-supplied arguments by using the overloaded method with one String array as the argument. `exec({"command", "arg1", "arg2"})`.


### 规则描述中文版
检测到 HTTPServletRequest 的输入进入 exec 的环境变量。建议使用单参数 String 数组形式的 exec，将命令与参数分开传入。

### 关联CWE
- CWE-454: External Initialization of Trusted Variables or Data Stores
