package com.example.owasp.a12;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * A12: 通过脚本引擎远程代码执行
 * 
 * 在 Java 中使用 JavaScript 或其他脚本鼓引擎可以导致 RCE，
 * 如果用户输入直接被评估为代码。
 */
public class RCEViaScriptEngine {

    /**
     * 漏洞: 直接评估用户输入的 JavaScript
     */
    public static Object evaluateUserExpression(String expression) 
            throws ScriptException {
        // 漏洞: 用户控制 JavaScript 代码
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        // 漏洞: 直接评伨用户输入
        return engine.eval(expression);
    }

    /**
     * 漏洞: 用户输入作为 JavaScript 函数
     * 
     * 攻击者可执行:
     * java.lang.Runtime.getRuntime().exec("rm -rf /")
     */
    public static Object executeMathExpression(String expression) 
            throws ScriptException {
        // 漏洞: 可程执行任意操作
        String script = "function calculate() { return " + expression + "; } calculate();";
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        return engine.eval(script);
    }

    /**
     * 漏洞: 模板脚本不验证撤输入的字符串拼接
     */
    public static String generateReport(String userData) 
            throws ScriptException {
        // 漏洞: 用户数据拼接到脚本中
        String script = "var data = '" + userData + "'; " +
                       "data.toUpperCase();";
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        return (String) engine.eval(script);
    }

    /**
     * 漏洞: Nashorn 引擎访问 Java 类
     */
    public static void executeNashornScript(String script) 
            throws ScriptException {
        // 漏洞: 可访问 Java Runtime
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        
        // Nashorn 允许访问 Java.lang.Runtime
        engine.eval(script);
    }

    /**
     * 安全: 限定表达式评估，並验证输入
     */
    public static double evaluateSimpleMath(String expression) 
            throws ScriptException {
        // 安全: 验证输入是简单数学表达式
        if (!expression.matches("[0-9+\\-*/(),\\.\\s]+")) {
            throw new IllegalArgumentException("无效表达式");
        }
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        return ((Number) engine.eval(expression)).doubleValue();
    }

    /**
     * 安全: 参数化脚本，无用户控制的代码
     */
    public static String processData(String userData) 
            throws ScriptException {
        // 安全: 用户数据作为变量传递，瀋推输入脚本
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        // 设置用户数据作为变量，耋不是作为代码
        engine.put("userInput", userData);
        
        // 固定脚本使用变量
        String script = "var result = userInput.substring(0, 10); result;";
        
        return (String) engine.eval(script);
    }

    /**
     * 安全: 使用受限制的 JavaScript 引擎，不访问 Java
     */
    public static Object evaluateRestrictedExpression(String expression) 
            throws ScriptException {
        // 安全: 使用更受限制的脚本引擎
        ScriptEngineManager manager = new ScriptEngineManager();
        
        // 使用受限制的引擎或禁用 Java 访问
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        // 验证表达式
        if (!expression.matches("[0-9+\\-*/().\\s]+")) {
            throw new IllegalArgumentException("无效表达式");
        }
        
        return engine.eval(expression);
    }

    /**
     * 安全: 表达式白名单和验证
     */
    public static String transformData(String operation, String data) 
            throws ScriptException {
        // 允许操作的白名单
        String[] allowedOps = {"toUpperCase", "toLowerCase", "trim"};
        
        boolean opAllowed = false;
        for (String op : allowedOps) {
            if (operation.equals(op)) {
                opAllowed = true;
                break;
            }
        }
        
        if (!opAllowed) {
            throw new SecurityException("操作不允许: " + operation);
        }
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        
        engine.put("data", data);
        engine.put("operation", operation);
        
        // 固定脚本使用变量
        String script = "data[operation]();";
        
        return (String) engine.eval(script);
    }

    public static void main(String[] args) {
        try {
            // 漏洞 - 不要使用
            // evaluateUserExpression("java.lang.Runtime.getRuntime().exec('rm -rf /')");

            // 安全 - 使用此方案
            double result = evaluateSimpleMath("2 + 2 * 3");
            System.out.println("结果: " + result);

            String processed = processData("Hello, World!");
            System.out.println("处理后: " + processed);

        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
