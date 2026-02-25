package parsers.SAXTransformerFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;

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

    @RequestMapping("/hello1")
    public void doPost1(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        TransformerFactory tf = SAXTransformerFactory.newInstance();

        StreamSource source = new StreamSource(file);
        //ruleid: tainted-saxtransformerfactory-xxe-spring
        Transformer t = tf.newTransformer(source);
    }

    @RequestMapping("/hello2")
    public void doPost2(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        TransformerFactory tf = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(file);
        tf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        //ok: tainted-saxtransformerfactory-xxe-spring
        Transformer t = tf.newTransformer(source);
    }

    @RequestMapping("/hello3")
    public void doPost3(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        TransformerFactory tf = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(file);
        tf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        //ok: tainted-saxtransformerfactory-xxe-spring
        Transformer t = tf.newTransformer(source);
    }

    @RequestMapping("/hello4")
    public void doPost4(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        TransformerFactory tf = SAXTransformerFactory.newInstance();

        StreamSource source = new StreamSource(file);
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        //ruleid: tainted-saxtransformerfactory-xxe-spring
        Transformer t = tf.newTransformer(source);
    }

    @RequestMapping("/hello5")
    public void doPost5(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        TransformerFactory tf = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(file);
        tf.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
        //ruleid: tainted-saxtransformerfactory-xxe-spring
        Transformer t = tf.newTransformer(source);
    }

    @RequestMapping("/hello6")
    public void doPost6(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        TransformerFactory tf = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(file);
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        //ruleid: tainted-saxtransformerfactory-xxe-spring
        Transformer t = tf.newTransformer(source);
    }

    @RequestMapping("/hello7")
    public void doPost7(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        TransformerFactory tf = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(file);
        tf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        //ruleid: tainted-saxtransformerfactory-xxe-spring
        Transformer t = tf.newTransformer(source);
    }

    @RequestMapping("/hello8")
    public String doPost8(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        try {
            TransformerFactory trf = SAXTransformerFactory.newInstance();
            //ok: tainted-saxtransformerfactory-xxe-spring
            Transformer transformer = trf.newTransformer();
            StreamSource source = new StreamSource(file);

            StringWriter out = new StringWriter();
            StreamResult result = new StreamResult(out);

            //ruleid: tainted-saxtransformerfactory-xxe-spring
            transformer.transform(source, result);
            return out.toString();
          } finally {}
    }

    @RequestMapping("/hello9")
    public String doPost9(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
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
            // ok: tainted-saxtransformerfactory-xxe-spring
            transformer.transform(domSource, result);

            return out.toString();
          } finally {}
    }
}
