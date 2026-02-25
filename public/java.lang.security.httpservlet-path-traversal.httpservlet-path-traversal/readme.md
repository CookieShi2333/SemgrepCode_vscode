##规则包名
java.lang.security.httpservlet-path-traversal.httpservlet-path-traversal
##规则ID
httpservlet-path-traversal

# java.lang.security.httpservlet-path-traversal.httpservlet-path-traversal

## 规则名称
java.lang.security.httpservlet-path-traversal.httpservlet-path-traversal

## 规则描述
Detected a potential path traversal. A malicious actor could control the location of this file, to include going backwards in the directory with '../'. To address this, ensure that user-controlled variables in file paths are sanitized. You may also consider using a utility method such as org.apache.commons.io.FilenameUtils.getName(...) to only retrieve the file name from the path.


### 规则描述中文版
检测到潜在路径遍历。攻击者可操纵路径，通过 '../' 等访问任意文件。请对文件路径中的用户可控变量进行净化，或使用 FilenameUtils.getName(...) 仅取文件名。

### 关联CWE
- CWE-22: Improper Limitation of a Pathname to a Restricted Directory ('Path Traversal')
