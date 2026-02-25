##规则包名
java.tars.jax-rs.deserialization.json4s-ast.jax-rs-json4s-ast-unsafe-deserialization-uri-params.jax-rs-json4s-ast-unsafe-deserialization-uri-params
##规则ID
jax-rs-json4s-ast-unsafe-deserialization-uri-params

# java.tars.jax-rs.deserialization.json4s-ast.jax-rs-json4s-ast-unsafe-deserialization-uri-params.jax-rs-json4s-ast-unsafe-deserialization-uri-params

## 规则名称
java.tars.jax-rs.deserialization.json4s-ast.jax-rs-json4s-ast-unsafe-deserialization-uri-params.jax-rs-json4s-ast-unsafe-deserialization-uri-params

## 规则描述
The application may convert user-controlled data into an object, which can lead to an insecure deserialization vulnerability. An attacker can create a malicious serialized object, pass it to the application, and take advantage of the deserialization process to perform Denial-of-service (DoS), Remote code execution (RCE), or bypass access control measures. To prevent this vulnerability, leverage data formats such as JSON or XML as safer alternatives. If you need to deserialize user input in a specific format, consider digitally signing the data before serialization to prevent tampering. For more information, see: [Deserialization prevention] (https://cheatsheetseries.owasp.org/cheatsheets/Deserialization_Cheat_Sheet.html) We do not recommend deserializing untrusted data with the `ObjectInputStream`. If you must, you can try overriding the `ObjectInputStream#resolveClass()` method or using a safe replacement for the generic `readObject()` method.


### 规则描述中文版
应用程序可能将用户可控数据反序列化为对象，导致不安全反序列化。攻击者可传入恶意序列化对象，利用反序列化过程造成拒绝服务（DoS）、远程代码执行（RCE）或绕过访问控制。建议使用 JSON/XML 等更安全格式；若必须反序列化用户输入，应在序列化前对数据做数字签名防篡改。不建议对不可信数据使用 ObjectInputStream/Kryo/Castor/XStream 等；若必须使用，请显式限制可反序列化的类型。详见 OWASP 反序列化防护备忘单。

### 关联CWE
- CWE-502: Deserialization of Untrusted Data
