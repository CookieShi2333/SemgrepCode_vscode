##规则包名
java.spring.security.injection.tainted-file-path.tainted-file-path
##规则ID
tainted-file-path

# java.spring.security.injection.tainted-file-path.tainted-file-path

## 规则名称
java.spring.security.injection.tainted-file-path.tainted-file-path

## 规则描述
Detected user input controlling a file path. An attacker could control the location of this file, to include going backwards in the directory with '../'. To address this, ensure that user-controlled variables in file paths are sanitized. You may also consider using a utility method such as org.apache.commons.io.FilenameUtils.getName(...) to only retrieve the file name from the path.


### 规则描述中文版
检测到用户输入控制文件路径，攻击者可能通过 '../' 等访问任意路径。请对路径中的用户可控变量进行净化，或使用 org.apache.commons.io.FilenameUtils.getName(...) 仅取文件名。

### 关联CWE
- CWE-23: Relative Path Traversal
