##规则包名
java.micronaut.path-traversal.file-taint-sls.file-taint-sls
##规则ID
file-taint-sls

# java.micronaut.path-traversal.file-taint-sls.file-taint-sls

## 规则名称
java.micronaut.path-traversal.file-taint-sls.file-taint-sls

## 规则描述
The application builds a file path from potentially untrusted data, which can lead to a path traversal vulnerability. An attacker can manipulate the path which the application uses to access files. If the application does not validate user input and sanitize file paths, sensitive files such as configuration or user data can be accessed, potentially creating or overwriting files. To prevent this vulnerability, validate and sanitize any input that is used to create references to file paths. Also, enforce strict file access controls. For example, choose privileges allowing public-facing applications to access only the required files. In Java, you may also consider using a utility method such as `org.apache.commons.io.FilenameUtils.getName(...)` to only retrieve the file name from the path.


### 规则描述中文版
应用根据可能不可信的数据构建文件路径，可能导致路径遍历。攻击者可操纵路径访问任意文件；若未校验并净化路径，可能访问、创建或覆盖敏感文件。防护建议：对用于构建路径的输入进行校验与净化，实施严格的文件访问控制；可考虑使用 org.apache.commons.io.FilenameUtils.getName(...) 仅取文件名。

### 关联CWE
- CWE-22: Improper Limitation of a Pathname to a Restricted Directory ('Path Traversal')
