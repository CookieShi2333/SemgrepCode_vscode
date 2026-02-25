package parsers.SchemaFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;

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

    static {
      File file = new File("test.json");
    }

    @RequestMapping("/hello1")
    public void doPost1(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      this.sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      //ruleid: tainted-schemafactory-xxe-spring
      Schema schema = this.sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello2")
    public void doPost2(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      //ok: tainted-schemafactory-xxe-spring
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello3")
    public void doPost3(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
      //ok: tainted-schemafactory-xxe-spring
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello4")
    public void doPost4(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      //ok: tainted-schemafactory-xxe-spring
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello5")
    public void doPost5(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      //ruleid: tainted-schemafactory-xxe-spring
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello6")
    public void doPost6(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      //ruleid: tainted-schemafactory-xxe-spring
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello7")
    public void doPost7(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      //ruleid: tainted-schemafactory-xxe-spring
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello8")
    public void doPost8(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      //ruleid: tainted-schemafactory-xxe-spring
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello9")
    public void doPost9(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      sf.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
      //ruleid: tainted-schemafactory-xxe-spring
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello10")
    public void doPost10(@RequestParam(defaultValue="test.txt") String xsd, @RequestParam String example) {
      File xsdFile = new File(xsd);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      //ok: tainted-schemafactory-xxe-spring
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }
}
