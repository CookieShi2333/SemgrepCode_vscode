package parsers.XMLReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.*;
// import org.xml.sax.helpers.XMLReaderFactory;

class FoobarIfs {

    private static XMLReader staticReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();

    static {
        staticReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    }

    public example0(HttpServletRequest req, SAXParser saxParser){
      String file = req.getParameter("file");
      File f = new File(file);
      XMLReader reader;

      if( saxParser == null ){
          reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      } else {
          reader = saxParser.getXMLReader();
      }

      // ruleid: tainted-xmlreader-xxe-servlet
      reader.parse(new InputSource(new InputStream(file)));
    }

    public example1(HttpServletRequest req, SAXParser saxParser){
        String file = req.getParameter("file");
        File f = new File(file);
        InputSource is = new InputSource(new InputStream(file));

        XMLReader reader = null;

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } else {
            reader = saxParser.getXMLReader();
        }

        // ruleid: tainted-xmlreader-xxe-servlet
        reader.parse(is);
    }

    public example2(HttpServletRequest req, SAXParser saxParser){

        String file = req.getParameter("file");
        File f = new File(file);
        InputSource is = new InputSource(new InputStream(file));

        XMLReader reader = null;

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        } else {
            reader = saxParser.getXMLReader();
        }

        // ruleid: tainted-xmlreader-xxe-servlet
        reader.parse(is);

    }

    public example3(HttpServletRequest req, SAXParser saxParser){

        String file = req.getParameter("file");
        File f = new File(file);
        InputSource is = new InputSource(new InputStream(file));

        XMLReader reader = null;

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } else {
            reader = saxParser.getXMLReader();
            reader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        }

        // ruleid: tainted-xmlreader-xxe-servlet
        reader.parse(is);
    }

    public example4(HttpServletRequest req, SAXParser saxParser){
        String file = req.getParameter("file");
        File f = new File(file);
        InputSource is = new InputSource(new InputStream(file));

        XMLReader reader = null;

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } else {
            reader = saxParser.getXMLReader();
        }

        // ok: tainted-xmlreader-xxe-servlet
        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        reader.parse(is);
    }
}
    
class Foobar {
    private SAXParser saxParser;

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      this.parser = SAXParserFactory.newInstance().newSAXParser();
      SAXParser saxParser = this.parser.newSAXParser();
      XMLReader reader = saxParser.getXMLReader();
      // ruleid: tainted-xmlreader-xxe-servlet
      reader.parse(is);
    }

    public void doPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      // ruleid: tainted-xmlreader-xxe-servlet
      reader.parse(is);
    }

    public void doPost3(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      // ruleid: tainted-xmlreader-xxe-servlet
      reader.parse(is);
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      // ok: tainted-xmlreader-xxe-servlet
      reader.parse(is);
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
      // ok: tainted-xmlreader-xxe-servlet
      reader.parse(is);
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
      // ok: tainted-xmlreader-xxe-servlet
      reader.parse(is);
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      // ok: tainted-xmlreader-xxe-servlet
      reader.parse(is);
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      try {
        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        //ruleid: tainted-xmlreader-xxe-servlet
        reader.parse(is);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      try {
        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        //ruleid: tainted-xmlreader-xxe-servlet
        reader.parse(is);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }


    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      File file = new File(req.getParameter("file"));
      InputSource is = new InputSource(new InputStream(file));
      try {
        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        //ok: tainted-xmlreader-xxe-servlet
        reader.parse(is);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
}