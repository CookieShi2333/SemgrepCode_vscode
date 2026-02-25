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

    static {
      File file = new File("test.json");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      this.sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      //ruleid: tainted-schemafactory-xxe-servlet
      Schema schema = this.sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    public void doPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      //ok: tainted-schemafactory-xxe-servlet
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    public void doPost3(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
      //ok: tainted-schemafactory-xxe-servlet
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      //ok: tainted-schemafactory-xxe-servlet
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    public void doPost5(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      //ruleid: tainted-schemafactory-xxe-servlet
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    public void doPost6(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      //ruleid: tainted-schemafactory-xxe-servlet
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    public void doPost7(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      //ruleid: tainted-schemafactory-xxe-servlet
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    public void doPost8(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      //ruleid: tainted-schemafactory-xxe-servlet
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    public void doPost9(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      sf.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
      //ruleid: tainted-schemafactory-xxe-servlet
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }

    public void doPost10(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File xsdFile = new File(req.getParameter("xsdFile"));
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      //ok: tainted-schemafactory-xxe-servlet
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(file));
    }
}