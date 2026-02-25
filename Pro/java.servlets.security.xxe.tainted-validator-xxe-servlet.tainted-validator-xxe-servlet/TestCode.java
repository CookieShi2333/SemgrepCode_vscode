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
    private static File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      this.sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = this.sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      //ruleid: tainted-validator-xxe-servlet
      validator.validate(new StreamSource(file));
    }

    public void doPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      //ok: tainted-validator-xxe-servlet
      validator.validate(new StreamSource(file));
    }

    public void doPost3(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      //ok: tainted-validator-xxe-servlet
      validator.validate(new StreamSource(file));
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
      //ok: tainted-validator-xxe-servlet
      validator.validate(new StreamSource(file));
    }

    public void doPost5(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
      //ok: tainted-validator-xxe-servlet
      validator.validate(new StreamSource(file));
    }

    public void doPost6(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      //ruleid: tainted-validator-xxe-servlet
      validator.validate(new StreamSource(file));
    }

    public void doPost7(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      //ruleid: tainted-validator-xxe-servlet
      validator.validate(new StreamSource(file));
    }

    public void doPost8(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      validator.setFeature("http://xml.org/sax/features/external-general-entities", false);
      //ruleid: tainted-validator-xxe-servlet
      validator.validate(new StreamSource(file));
    }

    public void doPost9(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

      //ruleid: tainted-validator-xxe-servlet
      validator.validate(new StreamSource(file));
    }
}