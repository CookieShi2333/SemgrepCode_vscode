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
    @RequestMapping("/hello1")
    public void doPost1(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        // ruleid: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello2")
    public void doPost2(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        // ruleid: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello3")
    public void doPost3(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        saxReader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        // ruleid: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello4")
    public void doPost4(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        saxReader.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        // ruleid: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello5")
    public void doPost5(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        saxReader.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        // ruleid: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello6")
    public void doPost6(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        // ok: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello7")
    public void doPost7(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        // ruleid: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello8")
    public void doPost8(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ruleid: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello9")
    public void doPost9(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        saxReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        saxReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ok: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello10")
    public void doPost10(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        EntityResolver noop = (publicId, systemId) -> new InputSource(new StringReader(""));
        saxReader.setEntityResolver(noop);
        // ok: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }

    @RequestMapping("/hello11")
    public void doPost11(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXReader saxReader = new SAXReader();
        saxReader.setIncludeExternalDTDDeclarations(false);
        // ruleid: tainted-saxreader-xxe-spring
        saxReader.read(file);
    }
}
