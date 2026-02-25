package com.example.owasp.a12;

import java.io.IOException;

/**
 * A12: 通过 ProcessBuilder 进行远程代码执行
 * 
 * ProcessBuilder 在正确使用时比 Runtime.exec() 更安全，但如果攻击者
 * 可以控制命令名称本身，仍然容易受到攻击。
 */
public class RCEViaProcessBuilder {

    /**
     * 漏洞: 攻击者可以控制要执行的命令
     */
    public static void executeArbitraryCommand(String userCommand) throws IOException {
        // 漏洞: 用户可以指定任意命令
        ProcessBuilder pb = new ProcessBuilder(userCommand);
        pb.start();
    }

    /**
     * 漏洞: 通过参数解析进行命令注入
     */
    public static void compileCppFile(String filename) throws IOException {
        // 漏洞: 如果分隔符不包含 shell 元字符
        String[] commandParts = filename.split("::");
        ProcessBuilder pb = new ProcessBuilder("g++", commandParts[0]);
        pb.start();
    }

    /**
     * 漏洞: 环境变量由攻击者控制
     */
    public static void runScript(String scriptName) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(scriptName);
        // 如果 PATH 环境由攻击者控制，他们可以执行恶意脚本
        java.util.Map<String, String> env = pb.environment();
        String path = System.getenv("PATH"); // 可能被攻击者控制
        env.put("PATH", path);
        pb.start();
    }

    /**
     * 安全: 固定命令，带验证的参数
     */
    public static void compileFileSecure(String filename) throws IOException {
        // 验证文件名
        if (!filename.matches("[a-zA-Z0-9._-]+\\.cpp$")) {
            throw new IllegalArgumentException("无效的文件名格式");
        }
        
        ProcessBuilder pb = new ProcessBuilder("g++", filename, "-o", filename + ".out");
        pb.start();
    }

    /**
     * 安全: 使用允许命令的白名单
     */
    public static void executeAllowedCommand(String command) throws IOException {
        // 允许命令的白名单
        String[] allowedCommands = {"ls", "pwd", "echo", "whoami"};
        
        boolean isAllowed = false;
        for (String allowed : allowedCommands) {
            if (command.equals(allowed)) {
                isAllowed = true;
                break;
            }
        }
        
        if (!isAllowed) {
            throw new IllegalArgumentException("命令不允许: " + command);
        }
        
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.start();
    }

    /**
     * 安全: 使用特定命令和受控的参数
     */
    public static void searchInFiles(String pattern, String directory) throws IOException {
        // 验证输入
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("搜索模式不能为空");
        }
        
        // 验证目录路径
        java.nio.file.Path path = java.nio.file.Paths.get(directory);
        if (!path.toFile().isDirectory()) {
            throw new IllegalArgumentException("无效的目录");
        }
        
        // 使用 ProcessBuilder 固定命令 'grep'
        ProcessBuilder pb = new ProcessBuilder("grep", "-r", pattern, directory);
        pb.start();
    }

    public static void main(String[] args) throws IOException {
        // 漏洞用法
        String userInput = "ls";
        // executeArbitraryCommand(userInput); // 不要这样做

        // 安全用法
        compileFileSecure("test.cpp");
        executeAllowedCommand("ls");
        searchInFiles("pattern", "/tmp");
    }
}
