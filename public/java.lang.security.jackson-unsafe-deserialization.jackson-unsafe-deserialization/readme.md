##规则包名
java.lang.security.jackson-unsafe-deserialization.jackson-unsafe-deserialization
##规则ID
jackson-unsafe-deserialization

# java.lang.security.jackson-unsafe-deserialization.jackson-unsafe-deserialization

## 规则名称
java.lang.security.jackson-unsafe-deserialization.jackson-unsafe-deserialization

## 规则描述
When using Jackson to marshall/unmarshall JSON to Java objects, enabling default typing is dangerous and can lead to RCE. If an attacker can control `$JSON` it might be possible to provide a malicious JSON which can be used to exploit unsecure deserialization. In order to prevent this issue, avoid to enable default typing (globally or by using "Per-class" annotations) and avoid using `Object` and other dangerous types for member variable declaration which creating classes for Jackson based deserialization.


### 规则描述中文版
Jackson 在将 JSON 与 Java 对象互转时启用 default typing 存在风险，可能被利用导致 RCE。请勿全局或按类启用 default typing，并避免在 Jackson 反序列化类中使用 Object 等危险类型。

### 关联CWE
- CWE-502: Deserialization of Untrusted Data
