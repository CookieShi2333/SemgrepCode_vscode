package parsers.SAXTransformerFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Node;


class Foobar {
    private Transformer trfm;

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        TransformerFactory tf = SAXTransformerFactory.newInstance();
    
        StreamSource source = new StreamSource(file);
        //ruleid: tainted-saxtransformerfactory-xxe-servlet
        Transformer t = tf.newTransformer(source);
    }

    public void doPost2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        TransformerFactory tf = TransformerFactory.newInstance();
    
        StreamSource source = new StreamSource(file);
        tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        //ok: tainted-saxtransformerfactory-xxe-servlet
        Transformer t = tf.newTransformer(source);
    }

    public void doPost3(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        TransformerFactory tf = TransformerFactory.newInstance();
    
        StreamSource source = new StreamSource(file);
        tf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        //ok: tainted-saxtransformerfactory-xxe-servlet
        Transformer t = tf.newTransformer(source);
    }

    public void doPost4(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        TransformerFactory tf = SAXTransformerFactory.newInstance();
    
        StreamSource source = new StreamSource(file);
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        //ruleid: tainted-saxtransformerfactory-xxe-servlet
        Transformer t = tf.newTransformer(source);
    }

    public void doPost5(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        TransformerFactory tf = TransformerFactory.newInstance();
    
        StreamSource source = new StreamSource(file);
        tf.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
        //ruleid: tainted-saxtransformerfactory-xxe-servlet
        Transformer t = tf.newTransformer(source);
    }

    public void doPost6(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        TransformerFactory tf = TransformerFactory.newInstance();
    
        StreamSource source = new StreamSource(file);
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        //ruleid: tainted-saxtransformerfactory-xxe-servlet
        Transformer t = tf.newTransformer(source);
    }

    public void doPost7(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        TransformerFactory tf = TransformerFactory.newInstance();
    
        StreamSource source = new StreamSource(file);
        tf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        //ruleid: tainted-saxtransformerfactory-xxe-servlet
        Transformer t = tf.newTransformer(source);
    }

    public String doPost8(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        try {
            TransformerFactory trf = SAXTransformerFactory.newInstance();
            //ok: tainted-saxtransformerfactory-xxe-servlet
            Transformer transformer = trf.newTransformer();
            StreamSource source = new StreamSource(file);

            StringWriter out = new StringWriter(); 
            StreamResult result = new StreamResult(out); 

            //ruleid: tainted-saxtransformerfactory-xxe-servlet
            transformer.transform(source, result); 
            return out.toString();
          } finally {}
    }

    public String doPost9(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String file = req.getParameter("file");
        try {
            TransformerFactory trf = SAXTransformerFactory.newInstance();
            DocumentBuilderFactory domFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = domFact.newDocumentBuilder();
            Document doc = builder.parse(new InputStream(file));
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            // ok: tainted-saxtransformerfactory-xxe-servlet
            transformer.transform(domSource, result);

            return out.toString();
          } finally {}
    }
}