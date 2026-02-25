##规则包名
java.lang.security.insecure-jms-deserialization.insecure-jms-deserialization
##规则ID
insecure-jms-deserialization

# java.lang.security.insecure-jms-deserialization.insecure-jms-deserialization

## 规则名称
java.lang.security.insecure-jms-deserialization.insecure-jms-deserialization

## 规则描述
JMS Object messages depend on Java Serialization for marshalling/unmarshalling of the message payload when ObjectMessage.getObject() is called. Deserialization of untrusted data can lead to security flaws; a remote attacker could via a crafted JMS ObjectMessage to execute arbitrary code with the permissions of the application listening/consuming JMS Messages. In this case, the JMS MessageListener consume an ObjectMessage type received inside the onMessage method, which may lead to arbitrary code execution when calling the $Y.getObject method.


### 规则描述中文版
JMS 对象消息依赖 Java 序列化，反序列化不可信数据可能导致安全问题；攻击者可通过恶意 ObjectMessage 在消费端执行任意代码。请避免消费不可信来源的 ObjectMessage，或在 getObject 前进行校验。

### 关联CWE
- CWE-502: Deserialization of Untrusted Data
