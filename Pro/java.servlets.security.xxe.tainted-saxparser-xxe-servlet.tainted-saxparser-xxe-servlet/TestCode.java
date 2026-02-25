package parsers.SAXParser;


import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;


class Foobar {

    public example0(HttpServletRequest req, SAXParser saxParser){

        SAXParser reader;
        File file = req.getParameter("file");

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser();
        } else {
            reader = saxParser.getXMLReader();
        }

        //reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        // ruleid: tainted-saxparser-xxe-servlet
        reader.parse(new InputStream(file), handler);
    }

    public example1(HttpServletRequest req, SAXParserFactory factory){
        SAXParserFactory f;
        File file = req.getParameter("file");

        if( factory == null ){
            f = SAXParserFactory.newInstance();
        } else {
            f = factory;
        }
        f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        SAXParser reader = f.newSAXParser();
        // ok: tainted-saxparser-xxe-servlet
        reader.parse(new InputStream(file), handler);
    }

    static {
        PrintAllHandlerSax handler = new PrintAllHandlerSax();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-servlet
        saxParser.parse(file, handler);
    }

    public void doPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
        PrintAllHandlerSax handler = new PrintAllHandlerSax();
        // ruleid: tainted-saxparser-xxe-servlet
        saxParser.parse(new InputStream(file), handler);
    }

    public void doPost3(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        SAXParser saxParser = factory.newSAXParser();
        // ok: tainted-saxparser-xxe-servlet
        saxParser.parse(new InputStream(file), handler);
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        SAXParser saxParser = factory.newSAXParser();
        // ok: tainted-saxparser-xxe-servlet
        saxParser.parse(new InputStream(file), handler);
    }

    public void doPost5(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        SAXParser saxParser = factory.newSAXParser();
        // ok: tainted-saxparser-xxe-servlet
        saxParser.parse(new InputStream(file), handler);
    }

    public void doPost6(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-servlet
        saxParser.parse(new InputStream(file), handler);
    }

    public void doPost7(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-servlet
        saxParser.parse(new InputStream(file), handler);
    }

    public void doPost8(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        // ok: tainted-saxparser-xxe-servlet
        saxParser.parse(new InputStream(file), handler);
    }

    public void doPost9(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-servlet
        saxParser.parse(new InputStream(file), handler);
    }

    public void doPost10(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/xinclude", false);
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-servlet
        saxParser.parse(new InputStream(file), handler);
    }

    public void doPost11(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        // ruleid: tainted-saxparser-xxe-servlet
        parser.parse(new InputStream(file), handler);
    }

    public void doPost12(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        // ok: tainted-saxparser-xxe-servlet
        parser.parse(new InputStream(file), handler);
    }

    public void doPost13(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
        // ok: tainted-saxparser-xxe-servlet
        parser.parse(new InputStream(file), handler);
    }

    //TODO:
    // `SAXParserFactory` initiated in the constructor
    // and then used in `todoExample`
    public Foobar() {
        //todoruleid: tainted-saxparser-xxe-servlet
        this.factory = SAXParserFactory.newInstance();
    }

    public void todoExample(File input) {
        SAXParser saxParser = this.factory.newSAXParser();
        saxParser.parse(input, new DefaultHandler());
    }
}