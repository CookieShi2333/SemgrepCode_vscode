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
import java.io.File;

import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

class Foobar {
    private SchemaFactory sf;
    private static File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");

    @RequestMapping("/hello1")
    public void doPost1(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      this.sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = this.sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      //ruleid: tainted-validator-xxe-spring
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello2")
    public void doPost2(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      //ok: tainted-validator-xxe-spring
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello3")
    public void doPost3(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      //ok: tainted-validator-xxe-spring
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello4")
    public void doPost4(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
      //ok: tainted-validator-xxe-spring
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello5")
    public void doPost5(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
      //ok: tainted-validator-xxe-spring
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello6")
    public void doPost6(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      //ruleid: tainted-validator-xxe-spring
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello7")
    public void doPost7(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      //ruleid: tainted-validator-xxe-spring
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello8")
    public void doPost8(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      validator.setFeature("http://xml.org/sax/features/external-general-entities", false);
      //ruleid: tainted-validator-xxe-spring
      validator.validate(new StreamSource(file));
    }

    @RequestMapping("/hello9")
    public void doPost9(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

      //ruleid: tainted-validator-xxe-spring
      validator.validate(new StreamSource(file));
    }
}
