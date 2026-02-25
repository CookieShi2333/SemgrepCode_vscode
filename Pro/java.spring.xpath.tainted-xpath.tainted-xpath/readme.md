##规则包名
java.spring.xpath.tainted-xpath.tainted-xpath
##规则ID
tainted-xpath

# java.spring.xpath.tainted-xpath.tainted-xpath

## 规则名称
java.spring.xpath.tainted-xpath.tainted-xpath

## 规则描述
XPath queries are constructed dynamically on user-controlled input. This could lead to XPath injection if variables passed into the evaluate or compile commands are not properly sanitized. Xpath injection could lead to unauthorized access to sensitive information in XML documents. Thoroughly sanitize user input or use parameterized XPath queries if you can.


### 规则描述中文版
XPath 查询基于用户可控输入动态构建，若传入 evaluate/compile 的变量未正确净化可能导致 XPath 注入，进而未授权访问 XML 中的敏感信息。请对用户输入进行充分净化或使用参数化 XPath 查询。

### 关联CWE
- CWE-643: Improper Neutralization of Data within XPath Expressions ('XPath Injection')
