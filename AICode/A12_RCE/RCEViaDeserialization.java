package com.example.owasp.a12;

import java.io.*;

/**
 * A12: 通过不安全反序列化远程代码执行
 * 
 * 反序列化不可信任数据可以通过 Commons Collections 等库中的
 * gadget chains 导致代码执行。
 */
public class RCEViaDeserialization {

    /**
     * 漏洞: 从不可信任来源反序列化任意对象
     */
    public static Object deserializeObject(byte[] data) 
            throws IOException, ClassNotFoundException {
        // 漏洞: 反序列化不可信任数据
        ByteArrayInputStream bas = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bas);
        
        Object obj = ois.readObject(); // Gadget chain 被汛可以在此阅读
        ois.close();
        
        return obj;
    }

    /**
     * 漏洞: 远程对象下载
     */
    public static Object loadRemoteObject(String url) 
            throws IOException, ClassNotFoundException {
        // 漏洞: 从不可信任来源加载序列化对象
        java.net.URLConnection conn = new java.net.URL(url).openConnection();
        ObjectInputStream ois = new ObjectInputStream(conn.getInputStream());
        
        Object obj = ois.readObject();
        ois.close();
        
        return obj;
    }

    /**
     * 漏洞: 直接流反序列化，不验证
     */
    public static Object readFromSocket(java.net.Socket socket) 
            throws IOException, ClassNotFoundException {
        // 漏洞: 来自网且对象的不可信任数据
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return ois.readObject();
    }

    /**
     * 安全: 使用类白名单验证对象
     */
    public static Object safeDeserialize(byte[] data) 
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream bas = new ByteArrayInputStream(data);
        
        // 安全: 自定义 ObjectInputStream 下筛类
        ObjectInputStream ois = new ObjectInputStream(bas) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) 
                    throws IOException, ClassNotFoundException {
                
                // 允许类的白名单
                String[] allowedClasses = {
                    "com.example.SafeData",
                    "java.util.ArrayList",
                    "java.util.HashMap",
                    "java.lang.String",
                    "java.lang.Integer"
                };
                
                // 检查类是否允许
                boolean allowed = false;
                for (String cls : allowedClasses) {
                    if (desc.getName().equals(cls)) {
                        allowed = true;
                        break;
                    }
                }
                
                if (!allowed) {
                    throw new InvalidClassException(
                        "反序列化类 " + desc.getName() + " 是不允许的"
                    );
                }
                
                return super.resolveClass(desc);
            }
        };
        
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    /**
     * 安全: 使用 JSON 代替 Java 序列化
     */
    public static Object deserializeJson(String json) 
            throws Exception {
        // 安全: 使用 JSON 库仅针对安全类的对象绑定
        com.google.gson.Gson gson = new com.google.gson.Gson();
        // 仅反序列化为特定的安全类
        return gson.fromJson(json, SafeData.class);
    }

    /**
     * 安全: 仅序列化安全数据
     */
    public static byte[] safeSerialize(SafeData data) 
            throws IOException {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bas);
        
        oos.writeObject(data);
        oos.close();
        
        return bas.toByteArray();
    }

    /**
     * 安全: 使用外部验证库 (NotSoSerial 等)
     */
    public static Object deserializeWithValidation(byte[] data) 
            throws IOException, ClassNotFoundException {
        // 实际场景中，使用 org.nibblesec.serialization.NotSoSerial
        // 或类似的验证库
        
        ByteArrayInputStream bas = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bas);
        
        // 在使用每个对象前验证
        Object obj = ois.readObject();
        
        if (obj instanceof SafeData) {
            return obj;
        } else {
            throw new ClassCastException("对象不是预有的安全类型");
        }
    }

    /**
     * 可以序列化的安全数据类
     */
    public static class SafeData implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String name;
        private int value;
        
        public SafeData(String name, int value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() {
            return name;
        }
        
        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        try {
            // 安全: 序列化安全数据
            SafeData data = new SafeData("test", 42);
            byte[] serialized = safeSerialize(data);
            
            // 安全: 验证反序列化
            Object restored = safeDeserialize(serialized);
            System.out.println("恢复的对象: " + restored);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
