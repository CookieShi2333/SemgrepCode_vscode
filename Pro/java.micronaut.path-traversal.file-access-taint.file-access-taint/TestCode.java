package com.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller("/hello")
public class HelloController {

  private static final String BASE_PATH ="/tmp/yoyo";

  @Get("/run{?cmd*}")
  public String cmdRun(Checker cmd) {
    String myPath = Paths.get(cmd.name).normalize().toString();

    File fff = new File(BASE_PATH, myPath);
    try {
      // ruleid: file-access-taint
      String content = new String(Files.readAllBytes(fff.toPath()));
      return content;
    } catch (IOException e) {
      e.printStackTrace();
      return "IO Error";
    }

  }

  @Get("/run-ok1{?cmd*}")
  public String cmdRunOk1(Checker cmd) throws Exception {
    String myPath = Paths.get(BASE_PATH, cmd.name).normalize().toString();

    if (!myPath.startsWith(BASE_PATH + File.separator)) {
      throw new Exception("Path traversal!");
    }

    File fff = new File(myPath);
    try {
      // ok: file-access-taint
      String content = new String(Files.readAllBytes(fff.toPath()));
      return content;
    } catch (IOException e) {
      e.printStackTrace();
      return "IO Error";
    }

  }

  @Get("/run-ok1-1{?cmd*}")
  public String cmdRunOk11(Checker cmd) throws Exception {
    File fff = new File(cmd.name);
    if (!fff.toPath().startsWith(BASE_PATH + File.separator)) {
      throw new Exception("Path traversal!");
    }
    try {
      // ok: file-access-taint
      String content = new String(Files.readAllBytes(fff.toPath()));
      return content;
    } catch (IOException e) {
      e.printStackTrace();
      return "IO Error";
    }

  }


  @Get("/run2{?cmd*}")
  public String cmdRun2(Checker cmd) throws IOException {
    String myPath = Paths.get(BASE_PATH, cmd.name).normalize().toString();

    char[] result = new char[100];

    FileReader input = new FileReader(myPath);
    // ruleid: file-access-taint
    input.read(result);

    return new String(result);
  }

  @Get("/run-ok2{?cmd*}")
  public String cmdRunOk2(Checker cmd) throws Exception {
    String fname = cmd.name;

    if (fname.contains("..") || fname.contains("/") || fname.contains("\\")) {
      throw new Exception("Path traversal");
    }

    String myPath = Paths.get(BASE_PATH, fname).normalize().toString();

    char[] result = new char[100];

    FileReader input = new FileReader(myPath);
    // ok: file-access-taint
    input.read(result);

    return new String(result);
  }

  @Get("/run3{?cmd*}")
  public String cmdRun3(Checker cmd) throws IOException {
    String myPath = Paths.get(BASE_PATH, cmd.name).normalize().toString();

    byte[] result = new byte[100];

    FileInputStream input = new FileInputStream(myPath);
    // ruleid: file-access-taint
    input.read(result);

    return new String(result);
  }

  @Get("/run-ok3{?cmd*}")
  public String cmdRunOk3(Checker cmd) throws IOException {
    // ok: file-access-taint
    FileInputStream result = new FileInputStream(cmd.name);
    return doSmth(result);
  }

  @Get("/run5{?cmd*}")
  public String cmdRun5(Checker cmd) throws IOException {
    String myPath = Paths.get(BASE_PATH, cmd.name).normalize().toString();

    String data = "This is a line of text inside the file.";

    FileOutputStream output = new FileOutputStream(myPath);
    byte[] result = data.getBytes();

    // ruleid: file-access-taint
    output.write(result);
    output.close();

    return new String(result);
  }

  @Get("/ok-run5-1{?cmd*}")
  public String cmdRun51(Checker cmd) throws IOException {
    String myPath = Paths.get(BASE_PATH, cmd.name).normalize().toString();
    // ok: file-access-taint
    FileOutputStream output = new FileOutputStream(myPath);
    return doSmth(output);
  }

  @Get("/run6{?cmd*}")
  public String cmdRun6(Checker cmd) throws IOException, TransformerException {
    String myPath = Paths.get(BASE_PATH, cmd.name).normalize().toString();
    TransformerFactory tFactory = TransformerFactory.newInstance();
    Source xslDoc = new StreamSource(myPath);
    Source xmlDoc = new StreamSource("report.xml");
    String outputFileName = "report.html";
    OutputStream htmlFile = new FileOutputStream(outputFileName);

    // ruleid: file-access-taint
    Transformer transform = tFactory.newTransformer(xslDoc);
    transform.transform(xmlDoc, new StreamResult(htmlFile));

    return "ok";
  }

  @Get("/ok-run6{?cmd*}")
  public String okCmdRun6(Checker cmd) throws IOException, TransformerException {
    String myPath = Paths.get(BASE_PATH, cmd.name).normalize().toString();
    if (myPath.contains("..") || myPath.contains("/") || myPath.contains("\\")) {
      throw new Exception("Path traversal");
    }
    TransformerFactory tFactory = TransformerFactory.newInstance();
    Source xslDoc = new StreamSource(myPath);
    Source xmlDoc = new StreamSource("report.xml");
    String outputFileName = "report.html";
    OutputStream htmlFile = new FileOutputStream(outputFileName);

    // ok: file-access-taint
    Transformer transform = tFactory.newTransformer(xslDoc);
    transform.transform(xmlDoc, new StreamResult(htmlFile));

    return "ok";
  }
}
