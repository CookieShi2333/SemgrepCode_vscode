##规则包名
java.spring.security.spring-tainted-code-execution.spring-tainted-code-execution
##规则ID
spring-tainted-code-execution

# java.spring.security.spring-tainted-code-execution.spring-tainted-code-execution

## 规则名称
java.spring.security.spring-tainted-code-execution.spring-tainted-code-execution

## 规则描述
User data flows into a script engine or another means of dynamic code evaluation. This is unsafe and could lead to code injection or arbitrary code execution as a result. Avoid inputting user data into code execution or use proper sandboxing if user code evaluation is necessary.


### 规则描述中文版
将未净化的用户输入传入脚本引擎或其它动态代码执行途径不安全，可能导致代码注入、数据泄露或任意代码执行。应避免此类用法，若确需执行用户代码，须使用适当的沙箱。

### 关联CWE
- CWE-95: Improper Neutralization of Directives in Dynamically Evaluated Code ('Eval Injection')
