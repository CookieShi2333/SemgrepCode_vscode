package com.example.restservice;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@RestController
public class GreetingController {

  @GetMapping("/test1")
  public String test1(@RequestParam(value = "name", defaultValue = "World") String name, HttpServletResponse response) {
    FileInputStream fileIS = new FileInputStream(this.getFile());
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    Document xmlDocument = builder.parse(fileIS);
    XPath xPath = XPathFactory.newInstance().newXPath();
    String expression = "/Employees/Employee[@emplid='" + name + "']";
    // ruleid: tainted-xpath
    NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
    doSmth(nodeList);
    return "ok";
  }

  @GetMapping("/test2")
  public String test2(@RequestBody CmdInfo cmd, HttpServletResponse response) {
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

  @GetMapping("/test3")
  public String test3(@RequestBody CmdInfo cmd, HttpServletResponse response) {
    FileInputStream fileIS = new FileInputStream(this.getFile());
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    Document xmlDocument = builder.parse(fileIS);
    XPath xPath = XPathFactory.newInstance().newXPath();
    String expression = "/Employees/Employee[@emplid='" + cmd.name + "']";
    // ruleid: tainted-xpath
    String result = xPath.evaluateExpression(expression, xmlDocument).getTextContent();
    return "ok " + result;
  }

  @GetMapping("/ok-test1")
  public String okTest1(@RequestParam(value = "name", defaultValue = "World") String name, HttpServletResponse response) {
    XPath xPath = XPathFactory.newInstance().newXPath();
    String expression = "/Employees/Employee[@emplid='123']";
    // ok: tainted-xpath
    NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getFileFrom(name), XPathConstants.NODESET);
    doSmth(nodeList);
    return "ok";
  }

  @GetMapping("/ok-test2")
  public String okTest2(@RequestBody CmdInfo cmd, HttpServletResponse response) {
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
