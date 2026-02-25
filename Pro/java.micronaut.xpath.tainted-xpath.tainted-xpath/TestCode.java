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

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;


@Controller("/hello")
public class HelloController {

  @Get("/test1{?cmd*}")
  public String cmdTest1(Foobar cmd) {
    FileInputStream fileIS = new FileInputStream(this.getFile());
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    Document xmlDocument = builder.parse(fileIS);
    XPath xPath = XPathFactory.newInstance().newXPath();
    String expression = "/Employees/Employee[@emplid='" + cmd.name + "']";
    // ruleid: tainted-xpath
    NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
    doSmth(nodeList);
    return "ok";
  }

  @Get("/test2{?cmd*}")
  public String cmdTest2(Foobar cmd) {
    FileInputStream fileIS = new FileInputStream(this.getFile());
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    Document xmlDocument = builder.parse(fileIS);
    XPath xPath = XPathFactory.newInstance().newXPath();
    String expression = "/Employees/Employee[@emplid='" + cmd.name + "']";
    // ruleid: tainted-xpath
    String result = xPath.evaluate(expression, xmlDocument);
    return "ok " + result;
  }

  @Get("/ok-test1{?cmd*}")
  public String cmdOkTest1(Session ses, Foobar cmd) {
    XPath xPath = XPathFactory.newInstance().newXPath();
    String expression = "/Employees/Employee[@emplid='123']";
    // ok: tainted-xpath
    NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getFileFrom(cmd.name), XPathConstants.NODESET);
    doSmth(nodeList);
    return "ok";
  }

  @Get("/ok-test2{?cmd*}")
  public String cmdOkTest2(Session ses, Foobar cmd) {
    FileInputStream fileIS = new FileInputStream(this.getFile(cmd.filePath));
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    Document xmlDocument = builder.parse(fileIS);
    XPath xPath = XPathFactory.newInstance().newXPath();
    String expression = "/Employees/Employee[@emplid='hardcode']";
    // ok: tainted-xpath
    String result = xPath.evaluate(expression, xmlDocument);
    return "ok " + result;
  }

}
