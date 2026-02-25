package com.example.owasp.a12;

import java.lang.reflect.Method;

/**
 * A12: 通过反射进行远程代码执行
 * 
 * 基于用户输入安全性使用 Java 反射来动态调用方法
 * 可以导致 RCE 漏洞。
 */
public class RCEViaReflection {

    /**
     * 漏洞: 基于用户输入的任意方法调用
     * 
     * 攻击者可以在任何可访问的类上调用任意公开方法
     */
    public static Object invokeMethod(String className, String methodName, Object... args) 
            throws Exception {
        // 漏洞: 不验证 className 或 methodName
        Class<?> clazz = Class.forName(className);
        Method method = clazz.getMethod(methodName);
        return method.invoke(null);
    }

    /**
     * 漏洞: 动态类实例化和方法调用
     */
    public static void executeUserClass(String className, String methodName) 
            throws Exception {
        // 漏洞: 可以实例化任意类並调用任意方法
        Object instance = Class.forName(className).newInstance();
        Method method = instance.getClass().getMethod(methodName);
        method.invoke(instance);
    }

    /**
     * 漏洞: 反射结合基于用户输入的的任意方法调用
     * 
     * 攻击者可以通过反射调用 Runtime 方法
     */
    public static void invokeRuntimeExec(String command) throws Exception {
        // 漏洞: 使用反射调用 Runtime.exec()
        Class<?> runtimeClass = Class.forName("java.lang.Runtime");
        Method getRuntime = runtimeClass.getMethod("getRuntime");
        Object runtime = getRuntime.invoke(null);
        
        Method exec = runtimeClass.getMethod("exec", String.class);
        exec.invoke(runtime, command);
    }

    /**
     * 漏洞: 由用户指定的参数进行构造函数调用
     */
    public static Object createInstance(String className, String arg) 
            throws Exception {
        // 漏洞: 用控主参数实例化任意类
        Class<?> clazz = Class.forName(className);
        java.lang.reflect.Constructor<?> constructor = 
            clazz.getConstructor(String.class);
        return constructor.newInstance(arg);
    }

    /**
     * 安全: 允许的类和方法的白名单
     */
    public static Object invokeAllowedMethod(String className, String methodName) 
            throws Exception {
        // 允许的类的白名单
        String[] allowedClasses = {
            "com.example.SafeClass",
            "com.example.UtilClass"
        };
        
        // 允许方法的白名单
        String[] allowedMethods = {
            "process",
            "transform",
            "validate"
        };
        
        // 验证 className
        boolean classAllowed = false;
        for (String allowed : allowedClasses) {
            if (className.equals(allowed)) {
                classAllowed = true;
                break;
            }
        }
        
        if (!classAllowed) {
            throw new SecurityException("类不允许: " + className);
        }
        
        // 验证 methodName
        boolean methodAllowed = false;
        for (String allowed : allowedMethods) {
            if (methodName.equals(allowed)) {
                methodAllowed = true;
                break;
            }
        }
        
        if (!methodAllowed) {
            throw new SecurityException("方法不允许: " + methodName);
        }
        
        Class<?> clazz = Class.forName(className);
        Method method = clazz.getMethod(methodName);
        return method.invoke(null);
    }

    /**
     * 安全: 正常操作使用卹按反射
     */
    public static void callSpecificMethod(Object target, String methodName) 
            throws Exception {
        // 仅允许特定方法名
        if (!methodName.matches("get[A-Z][a-zA-Z]*")) {
            throw new IllegalArgumentException("无效的方法名模式");
        }
        
        Method method = target.getClass().getMethod(methodName);
        method.invoke(target);
    }

    /**
     * 安全: 使用接口而不是反射的可扩展性
     */
    public interface SafeOperation {
        Object execute();
    }

    public static Object executeSafeOperation(SafeOperation operation) {
        // 类型安全的反射替代方案
        return operation.execute();
    }

    public static void main(String[] args) {
        try {
            // 漏洞 - 不要使用
            // invokeMethod("java.lang.Runtime", "getRuntime");
            // invokeRuntimeExec("rm -rf /");

            // 安全 - 使用此方案
            executeSafeOperation(() -> {
                return "\u5b89全操作结果";
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
