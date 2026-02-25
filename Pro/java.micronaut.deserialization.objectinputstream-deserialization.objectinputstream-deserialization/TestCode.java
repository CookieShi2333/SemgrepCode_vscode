package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.session.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.apache.commons.io.input.ClassLoaderObjectInputStream;
import java.io.*;

@Controller("/hello")
public class HelloController {

  @Get("/test1{?cmd*}")
  public String test1(Foobar cmd) {

    String b64token;
    long before;
    long after;
    int delay;

    b64token = cmd.token.replace('-', '+').replace('_', '/');

    try (ObjectInputStream ois =
        // ruleid: objectinputstream-deserialization
        new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(b64token)))) {
      before = System.currentTimeMillis();
      Object o = ois.readObject();
      if (!(o instanceof VulnerableTaskHolder)) {
        if (o instanceof String) {
          return "insecure-deserialization.invalidversion";
        }
          return "insecure-deserialization.invalidversion";
      }
      after = System.currentTimeMillis();
    } catch (InvalidClassException e) {
      return "insecure-deserialization.invalidversion";
    } catch (IllegalArgumentException e) {
      return "insecure-deserialization.invalidversion";
    } catch (Exception e) {
      return "insecure-deserialization.invalidversion";
    }
    return "ok";
  }

  @Get("/test2{?cmd*}")
  public String test2(Foobar cmd) {

    String b64token;
    long before;
    long after;
    int delay;

    b64token = cmd.token.replace('-', '+').replace('_', '/');

    try (ObjectInputStream ois =
        // ruleid: objectinputstream-deserialization
        new ClassLoaderObjectInputStream(Thread.currentThread().getContextClassLoader(), new ByteArrayInputStream(Base64.getDecoder().decode(b64token)))) {
      before = System.currentTimeMillis();
      Object o = ois.readObject();
      if (!(o instanceof VulnerableTaskHolder)) {
        if (o instanceof String) {
          return "insecure-deserialization.invalidversion";
        }
          return "insecure-deserialization.invalidversion";
      }
      after = System.currentTimeMillis();
    } catch (InvalidClassException e) {
      return "insecure-deserialization.invalidversion";
    } catch (IllegalArgumentException e) {
      return "insecure-deserialization.invalidversion";
    } catch (Exception e) {
      return "insecure-deserialization.invalidversion";
    }
    return "ok";
  }

  @Get("/okTest1{?cmd*}")
  public String okTest1(Foobar cmd) {

    FileInputStream fis = new FileInputStream("t.tmp");
    // ok: objectinputstream-deserialization
    ObjectInputStream ois = new ObjectInputStream(fis);

    int i = ois.readInt();
    String today = (String) ois.readObject();
    Date date = (Date) ois.readObject();

    ois.close();

    return "ok";
  }

}
