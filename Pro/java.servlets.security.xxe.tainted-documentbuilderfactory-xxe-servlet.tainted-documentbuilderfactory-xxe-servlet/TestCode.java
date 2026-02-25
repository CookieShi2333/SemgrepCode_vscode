package example;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;

class BadDocumentBuilderFactoryStatic {
    public doPost1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        File file = req.getParameter("file");
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // ruleid: tainted-documentbuilderfactory-xxe-servlet
        db.parse(new InputSource(file));
    }

    public doPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        File file = req.getParameter("file");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new Inputstream(req.getParameter("file"));
        //ruleid: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }

    public doPost3 (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ok: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }

    public void doPost4(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ok: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }

    public void doPost5(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ok: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }

    public void doPost6(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // general entities is missing
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ruleid: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }

    public void doPost7(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ok: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }


    public void doPost8(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ruleid: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }


    public void doPost9(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ruleid: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }

    public void doPost10(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ok: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }

    public void doPost11(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setExpandEntityReferences(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ruleid: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }

    public void doPost12(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setXIncludeAware(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //ruleid: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }

    public void doPost13(HttpServletRequest req){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                return new InputSource(new StringReader(""));
            }
        });

        InputStream is = new Inputstream(req.getParameter("file"));
        //ok: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }
}

class ObjectCreation {

    private DocumentBuilderFactory dbf;

    static {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
    }

    public void doPost5(HttpServletRequest req){
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new Inputstream(req.getParameter("file"));
        //todook: tainted-documentbuilderfactory-xxe-servlet
        db.parse(is);
    }
}