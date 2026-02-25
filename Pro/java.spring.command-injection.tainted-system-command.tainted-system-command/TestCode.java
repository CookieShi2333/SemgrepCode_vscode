package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.context.request.WebRequest;

@RestController
public class GreetingController {

  @Autowired
  SmthElse smthElse;

  @GetMapping("/test1")
  public String test1(@RequestParam(value = "name", defaultValue = "World") String name, HttpServletResponse response) {
    ProcessBuilder processBuilder = new ProcessBuilder();
    String cmd = "/usr/games/cowsay '" + name + "'";
    System.out.println(cmd);
    // ruleid: tainted-system-command
    processBuilder.command("bash", "-c", cmd);

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return output.toString();
  }

  @GetMapping("/test2")
  public String test2(@RequestBody CmdInfo cmd, HttpServletResponse response) {
    try {
      String comd = "ls -lah " + cmd.name;
      // ruleid: tainted-system-command
      Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", comd});
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
      doSmth(stdInput);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
    }
    return "ok";
  }

  @GetMapping("/test3/{name}")
  public String test3(WebRequest request) {
    String name = request.getParameter("name");

    try {
        // ruleid: tainted-system-command
        ProcessBuilder processBuilder = new ProcessBuilder("ls -lah " + name);
        Process process = processBuilder.start();
        processBuilder.redirectErrorStream(true);

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            doSmth(stdInput);
        }
    } catch (IOException | InterruptedException e) {
        System.out.println("Error: " + e.getMessage());
    }
    return "ok";
  }

  @GetMapping("/test4/{name}")
  public String test4(WebRequest request) {
    String name = request.getParameter("name");

    try {
        HashMap<String, String> cmd = new HashMap<String, String>();
        cmd.put("command", "ls -lah " + name);
        // ruleid: tainted-system-command
        ProcessBuilder processBuilder = new ProcessBuilder(cmd.get("command"));
        Process process = processBuilder.start();
        processBuilder.redirectErrorStream(true);

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            doSmth(stdInput);
        }
    } catch (IOException | InterruptedException e) {
        System.out.println("Error: " + e.getMessage());
    }
    return "ok";
  }

  @GetMapping("/test5/{name}")
  public String test5(WebRequest request) {
    String name = request.getParameter("name");

    try {
        StringBuilder sb = new StringBuilder();
        sb.append("ls");
        sb.append(" -lah");
        sb.append(name);
        // ruleid: tainted-system-command
        ProcessBuilder processBuilder = new ProcessBuilder(sb.toString());
        Process process = processBuilder.start();
        processBuilder.redirectErrorStream(true);

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            doSmth(stdInput);
        }
    } catch (IOException | InterruptedException e) {
        System.out.println("Error: " + e.getMessage());
    }
    return "ok";
  }

  @GetMapping("/ok-test1")
  public String okTest1(@RequestParam(value = "name", defaultValue = "World") String name, HttpServletResponse response) {
    try {
      String comd = "ls -lah /tmp/file" + Integer.parseInt(name);
      // ok: tainted-system-command
      Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", comd});
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
      doSmth(stdInput);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
    }
    return "ok";
  }

  @GetMapping("/ok-test2")
  public String okTest2(@RequestBody String name, HttpServletResponse response) {
    try {
      String comd = "ls -lah /tmp/file" + (name.equals("yo") ? "one" : "two");
      // ok: tainted-system-command
      Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", comd});
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
      doSmth(stdInput);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
    }
    return "ok";
  }

  public String okTest3(String notUserInput) {
    try {
      String comd = "ls -lah " + notUserInput;
      // ok: tainted-system-command
      Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", comd});
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
      doSmth(stdInput);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
    }
    return "ok";
  }

}
