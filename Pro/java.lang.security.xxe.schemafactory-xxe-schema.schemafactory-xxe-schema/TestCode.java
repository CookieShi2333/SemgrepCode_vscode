package parsers.SchemaFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

class Foobar {

    private SchemaFactory sf;

    public Foobar(File input) {
        this.sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //ruleid: schemafactory-xxe-schema
        Schema schema = this.sf.newSchema(xsdFile);
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(input));
    }

    public void doSmth(File input) {
        var sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        //ruleid: schemafactory-xxe-schema
        var schema = sf.newSchema(xsdFile);
        var validator = schema.newValidator();
        validator.validate(new StreamSource(input));
    }

    public void doSmthSecure(File input) {
        var sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        //ok: schemafactory-xxe-schema
        var schema = sf.newSchema(xsdFile);
        var validator = schema.newValidator();
        validator.validate(new StreamSource(input));
    }

}


public class Tests {

  static File xmlFile = new File("payloads/input-dos/xml-bomb.xml");
  static File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");

  public static void main(String [] args) {
    // ignore error messages
    System.setErr(new PrintStream(new OutputStream() {
        public void write(int b) {
        }
    }));

    try {
      System.out.println("TESTING SchemaFactory configurations");
      testDefaultConfig();
      testSetFeatureSecreProcessing();
      testAccessExternalDTD();
      testAccessExternalSchema();
      testAccessExternalStylesheet();
      testDisallowDoctypeDecl();
      testExternalGeneralEntities();
      testExternalParameterEntities();
      testSetValidating();
      testSetExpandEntities();
      testSetXIncludeAware();
      testXInclude();
      testLoadExternalDTD();
    } catch (Exception e) {
        System.out.println(e.getMessage());
        // do nothing
    }
  }

  public static void testDefaultConfig() {
    System.out.println("Default Config");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    //ruleid: schemafactory-xxe-schema
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.validate(new StreamSource(xmlFile));
  }

  public static void testSetFeatureSecreProcessing() throws TransformerConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    //ok: schemafactory-xxe-schema
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.validate(new StreamSource(xmlFile));
  }

  public static void testSetFeatureSecreProcessing2() throws TransformerConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
    //ok: schemafactory-xxe-schema
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.validate(new StreamSource(xmlFile));
  }

  public static void testAccessExternalDTD() throws TransformerConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    //ruleid: schemafactory-xxe-schema
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.validate(new StreamSource(xmlFile));
  }

  public static void testAccessExternalSchema() throws SAXNotRecognizedException, SAXNotSupportedException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    //ok: schemafactory-xxe-schema
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.validate(new StreamSource(xmlFile));
  }

  public static void testAccessExternalSchema2() throws SAXNotRecognizedException, SAXNotSupportedException {
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setProperty("http://javax.xml.XMLConstants/property/accessExternalSchema", "");
    //ok: schemafactory-xxe-schema
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.validate(new StreamSource(xmlFile));
  }

  public static void testAccessExternalStylesheet() throws TransformerConfigurationException {
    System.out.println("ACCESS_EXTERNAL_STYLESHEET - n/a");
  }

  public static void testDisallowDoctypeDecl() {
    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      //ruleid: schemafactory-xxe-schema
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(xmlFile));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalGeneralEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://xml.org/sax/features/external-general-entities", true);
      //ruleid: schemafactory-xxe-schema
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(xmlFile));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalParameterEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
      //ruleid: schemafactory-xxe-schema
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(xmlFile));
    } catch (Exception e) {
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
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://apache.org/xml/features/xinclude", false);
      //ruleid: schemafactory-xxe-schema
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(xmlFile));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testLoadExternalDTD() {
    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      //ruleid: schemafactory-xxe-schema
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(xmlFile));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
