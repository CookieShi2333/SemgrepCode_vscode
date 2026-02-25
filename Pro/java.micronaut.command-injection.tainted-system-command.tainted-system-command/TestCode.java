package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.session.Session;

import java.util.UUID;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Controller("/hello")
public class HelloController {

  @Get("/test1{?cmd*}")
  public String cmdTest1(Foobar cmd) {
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

  @Get("/test2{?cmd*}")
  public String cmdTest2(Foobar cmd) {
    try {
      String comd = "ls -lah " + cmd.name;
      // ruleid: tainted-system-command
      Process p =  new ProcessBuilder(new String[]{"bash", "-c", comd}).start();
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
      doSmth(stdInput);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
    }
    return "ok";
  }

  @Get("/test2{?cmd*}")
  public String cmdOkTest2(Session ses, Foobar cmd) {
    try {
      String comd = "ls -lah " + ses.get("name");
      // ok: tainted-system-command
      Process p =  new ProcessBuilder(new String[]{"bash", "-c", comd}).start();
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
      doSmth(stdInput);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
    }
    return "ok";
  }

  @Get("/test2-1{?cmd*}")
  public String cmdOkTest21(UUID id, Foobar cmd) {
    try {
      String comd = "ls -lah " + id;
      // ok: tainted-system-command
      Process p =  new ProcessBuilder(new String[]{"bash", "-c", comd}).start();
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
      doSmth(stdInput);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
    }
    return "ok";
  }

  @Post("/test3")
  public String cmdTest3(HttpRequest<?> request) {
      Foobar cmd = request.getBody(Foobar.class).orElse(null);
      if (cmd == null) {
          return "Invalid request body";
      }

      try {
          // ruleid: tainted-system-command
          ProcessBuilder processBuilder = new ProcessBuilder("ls -lah " + cmd.name);
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

  @Post("/test4")
  public String cmdTest4(String cmd) {

      try {
          ProcessBuilder processBuilder = new ProcessBuilder();
          // ruleid: tainted-system-command
          Process process = processBuilder.command("ls -lah " + cmd).start();
          processBuilder.redirectErrorStream(true);

          try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
              doSmth(stdInput);
          }
      } catch (IOException | InterruptedException e) {
          System.out.println("Error: " + e.getMessage());
      }
      return "ok";
  }

  @Post("/test5")
  public String cmdTest5(Integer cmd) {
      try {
          ProcessBuilder processBuilder = new ProcessBuilder();
          // ok: tainted-system-command
          Process process = processBuilder.command("ls -lah /tmp/file" + cmd).start();
          processBuilder.redirectErrorStream(true);

          try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
              doSmth(stdInput);
          }
      } catch (IOException | InterruptedException e) {
          System.out.println("Error: " + e.getMessage());
      }
      return "ok";
  }

  @Get("/test6{?cmd*}")
  public String cmdTest6(Foobar cmd) {
    try {
      String comd = "ls -lah /tmp/file" + Integer.parseInt(cmd.name);
      // ok: tainted-system-command
      Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", comd});
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
      doSmth(stdInput);
    } catch (IOException e) {
      System.out.println("error: " + e.getMessage());
    }
    return "ok";
  }

  @Get("/test7{?cmd*}")
  public String cmdTest7(Foobar cmd) {
    try {
      String comd = "ls -lah /tmp/file" + (cmd.name.equals("yo") ? "one" : "two");
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
