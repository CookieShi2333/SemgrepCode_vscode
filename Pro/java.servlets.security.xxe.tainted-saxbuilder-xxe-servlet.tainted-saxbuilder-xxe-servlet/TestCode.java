package parsers.SAXBuilder;

import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;


class Foobar {

    private SAXBuilder saxBuilder;

    public doPost1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        this.saxBuilder = new SAXBuilder();
        StringReader reader = new StringReader(req.getParameter("xml"));
        // ruleid: tainted-saxbuilder-xxe-servlet
        this.saxBuilder.build(reader);
    }

    public void doPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        SAXBuilder saxBuilder = new SAXBuilder(XMLReaders.XSDVALIDATING);
        StringReader reader = new StringReader(req.getParameter("xml"));
        // ok: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(reader);
    }

    public void doPost3(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder(XMLReaders.DTDVALIDATING);
        // ok: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        // ruleid: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost5(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        // ok: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost6(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        // ruleid: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost7(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        // ok: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost8(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://xml.org/sax/features/external-general-entities", false);
        // ruleid: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost9(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ruleid: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost10(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://xml.org/sax/features/external-general-entities", false);
        saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ok: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost11(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setExpandEntities(false);
        saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ok: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost12(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/xinclude", false);
        // ruleid: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost13(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        // ruleid: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }

    public void doPost14(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setExpandEntities(false);
        // ruleid: tainted-saxbuilder-xxe-servlet
        saxBuilder.build(input);
    }
}