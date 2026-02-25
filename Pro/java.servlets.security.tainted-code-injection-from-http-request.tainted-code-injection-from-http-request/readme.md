##规则包名
java.servlets.security.tainted-code-injection-from-http-request.tainted-code-injection-from-http-request
##规则ID
tainted-code-injection-from-http-request

# java.servlets.security.tainted-code-injection-from-http-request.tainted-code-injection-from-http-request

## 规则名称
java.servlets.security.tainted-code-injection-from-http-request.tainted-code-injection-from-http-request

## 规则描述
Passing unsanitized user input to a Script Engine or other means of dynamic code evaluation is unsafe. This could lead to code injection with data leakage or arbitrary code execution as a result. Avoid this, or use proper sandboxing if user code evaluation is intended.


### 规则描述中文版
将未净化的用户输入传入脚本引擎或其它动态代码执行途径不安全，可能导致代码注入、数据泄露或任意代码执行。应避免此类用法，若确需执行用户代码，须使用适当的沙箱。

### 关联CWE
- CWE-95: Improper Neutralization of Directives in Dynamically Evaluated Code ('Eval Injection')
