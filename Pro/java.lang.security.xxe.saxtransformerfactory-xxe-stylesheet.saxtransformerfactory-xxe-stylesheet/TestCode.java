package parsers.SAXTransformerFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Node;


class Foobar {

    private Transformer trfm;

    public Foobar(File input) {
        TransformerFactory tf = SAXTransformerFactory.newInstance();
        //ruleid: saxtransformerfactory-xxe-stylesheet
        this.trfm = tf.newTransformer();
    }

    public void doSmth(File input) {
        //ruleid: saxtransformerfactory-xxe-stylesheet
        var trfm = TransformerFactory.newInstance().newTransformer();
    }

    public void doSmthSecure(File input) {
        TransformerFactory tf = SAXTransformerFactory.newInstance();
        tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        var trfm = tf.newTransformer();
    }

}


public class Tests {
    static File input = new File("payloads/input-dos/xml-bomb.xml");
    static File style = new File("payloads/input-with-stylesheet/ok-stylesheet.xsl");
    static File output = new File("payloads/input-with-stylesheet/output.csv");

  public static void testDefaultConfig() {
    System.out.println("Default Config");
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    //ruleid: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ruleid: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);

    System.out.println("Default Config");
    TransformerFactory tf = TransformerFactory.newInstance();
    //ruleid: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ruleid: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);
  }

  public static void testSetFeatureSecreProcessing() throws TransformerConfigurationException {
    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    parseAll(tf);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);


    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    parseAll(tf);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);

  }

  public static void testSetFeatureSecreProcessing2() throws TransformerConfigurationException {
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    tf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
    parseAll(tf);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);


    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
    parseAll(tf);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);

  }

  public static void testAccessExternalDTD() throws TransformerConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    parseAll(tf);
    //ruleid: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ruleid: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);


    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    parseAll(tf);
    //ruleid: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ruleid: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);

  }

  public static void testAccessExternalSchema() {
    System.out.println("ACCESS_EXTERNAL_SCHEMA is n/a");
  }

  public static void testAccessExternalStylesheet() throws TransformerConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, \"\")");
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
    parseAll(tf);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);

    
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, \"\")");
    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
    parseAll(tf);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);

  }


  public static void testAccessExternalStylesheet2() throws TransformerConfigurationException {
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    tf.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");
    parseAll(tf);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);

    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");
    parseAll(tf);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer();

    StreamSource source = new StreamSource(input);
    StreamSource xsltSource = new StreamSource(style);
    //ok: saxtransformerfactory-xxe-stylesheet
    Transformer t = tf.newTransformer(xsltSource);

  }

  public static void testDisallowDoctypeDecl() {
    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    try {
      tf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);

    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
    TransformerFactory tf = TransformerFactory.newInstance();
    try {
      tf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalGeneralEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    try {
      tf.setFeature("http://xml.org/sax/features/external-general-entities", true);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
    TransformerFactory tf = TransformerFactory.newInstance();
    try {
      tf.setFeature("http://xml.org/sax/features/external-general-entities", true);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalParameterEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    try {
      tf.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
    TransformerFactory tf = TransformerFactory.newInstance();
    try {
      tf.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testSetValidating() {
    System.out.println("setValidating(false) - n/a");
  }

  public static void testSetExpandEntities() {
    System.out.println("setExpandEntityReferences(false) - n/a");
  }

  public static void testSetXIncludeAware() {
    System.out.println("setXIncludeAware(false) - n/a");
  }

  public static void testXInclude() {
    System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    try {
      tf.setFeature("http://apache.org/xml/features/xinclude", false);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
    TransformerFactory tf = TransformerFactory.newInstance();
    try {
      tf.setFeature("http://apache.org/xml/features/xinclude", false);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testLoadExternalDTD() {
    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
    TransformerFactory tf = SAXTransformerFactory.newInstance();
    try {
      tf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }

    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
    TransformerFactory tf = TransformerFactory.newInstance();
    try {
      tf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      parseAll(tf);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer();

      StreamSource source = new StreamSource(input);
      StreamSource xsltSource = new StreamSource(style);
      //ruleid: saxtransformerfactory-xxe-stylesheet
      Transformer t = tf.newTransformer(xsltSource);
    } catch (TransformerConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  public String domTransformer() {
    try {
      TransformerFactory trf = SAXTransformerFactory.newInstance();
      //ok: saxtransformerfactory-xxe-stylesheet
      Transformer transformer = trf.newTransformer();
      Node dom = getDomSomewhere();
      DOMSource source = new DOMSource(dom);
      StringWriter out = new StringWriter(); 
      StreamResult result = new StreamResult(out); 
      transformer.transform(source, result); 
      return out.toString();
    } finally {}
  }

  public String domTransformer2() {
    try {
      TransformerFactory trf = SAXTransformerFactory.newInstance();
      //ok: saxtransformerfactory-xxe-stylesheet
      Transformer transformer = trf.newTransformer();
      Node dom = getDomSomewhere();
      Source source = new DOMSource(dom);
      StringWriter out = new StringWriter(); 
      StreamResult result = new StreamResult(out); 
      transformer.transform(source, result); 
      return out.toString();
    } finally {}
  }
}

