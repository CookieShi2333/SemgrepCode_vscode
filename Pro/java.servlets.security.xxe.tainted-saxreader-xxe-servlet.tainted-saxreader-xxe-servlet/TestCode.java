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
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        // ruleid: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        // ruleid: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost3(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        saxReader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        // ruleid: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        saxReader.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        // ruleid: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost5(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        saxReader.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        // ruleid: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost6(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        // ok: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost7(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        // ruleid: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost8(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ruleid: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost9(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ok: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost10(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        EntityResolver noop = (publicId, systemId) -> new InputSource(new StringReader(""));
        saxReader.setEntityResolver(noop);
        // ok: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }

    public void doPost11(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        SAXReader saxReader = new SAXReader();
        saxReader.setIncludeExternalDTDDeclarations(false);
        // ruleid: tainted-saxreader-xxe-servlet
        saxReader.read(file);
    }
}