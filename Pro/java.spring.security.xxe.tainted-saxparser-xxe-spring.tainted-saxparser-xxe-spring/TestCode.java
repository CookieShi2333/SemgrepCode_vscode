package parsers.SAXParser;


import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;


class Foobar {
    static {
        PrintAllHandlerSax handler = new PrintAllHandlerSax();
    }

    @RequestMapping("/foo0")
    public void example0(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {

        SAXParser reader;

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser();
        } else {
            reader = saxParser.getXMLReader();
        }

        //reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        // ruleid: tainted-saxparser-xxe-spring
        reader.parse(new InputStream(file), handler);
    }

    @RequestMapping("/foo1")
    public void example1(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory f;

        if( factory == null ){
            f = SAXParserFactory.newInstance();
        } else {
            f = factory;
        }
        f.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        SAXParser reader = f.newSAXParser();
        // ok: tainted-saxparser-xxe-spring
        reader.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello1")
    public void doPost1(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-spring
        saxParser.parse(file, handler);
    }

    @RequestMapping("/hello2")
    public void doPost2(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
        PrintAllHandlerSax handler = new PrintAllHandlerSax();
        // ruleid: tainted-saxparser-xxe-spring
        saxParser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello3")
    public void doPost3(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        SAXParser saxParser = factory.newSAXParser();
        // ok: tainted-saxparser-xxe-spring
        saxParser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello4")
    public void doPost4(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        SAXParser saxParser = factory.newSAXParser();
        // ok: tainted-saxparser-xxe-spring
        saxParser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello5")
    public void doPost5(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        SAXParser saxParser = factory.newSAXParser();
        // ok: tainted-saxparser-xxe-spring
        saxParser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello6")
    public void doPost6(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-spring
        saxParser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello7")
    public void doPost7(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-spring
        saxParser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello8")
    public void doPost8(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        // ok: tainted-saxparser-xxe-spring
        saxParser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello9")
    public void doPost9(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-spring
        saxParser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello10")
    public void doPost10(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/xinclude", false);
        SAXParser saxParser = factory.newSAXParser();
        // ruleid: tainted-saxparser-xxe-spring
        saxParser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello11")
    public void doPost11(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        // ruleid: tainted-saxparser-xxe-spring
        parser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello12")
    public void doPost12(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        // ok: tainted-saxparser-xxe-spring
        parser.parse(new InputStream(file), handler);
    }

    @RequestMapping("/hello13")
    public void doPost13(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
        // ok: tainted-saxparser-xxe-spring
        parser.parse(new InputStream(file), handler);
    }
}
