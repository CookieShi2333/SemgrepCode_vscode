package parsers.SAXBuilder;

import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;


class Foobar {

    private SAXBuilder saxBuilder;

    @RequestMapping("/hello1")
    public void doPost1(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        this.saxBuilder = new SAXBuilder();
        StringReader reader = new StringReader(file);
        // ruleid: tainted-saxbuilder-xxe-spring
        this.saxBuilder.build(reader);
    }

    @RequestMapping("/hello2")
    public void doPost2(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        SAXBuilder saxBuilder = new SAXBuilder(XMLReaders.XSDVALIDATING);
        StringReader reader = new StringReader(file);
        // ok: tainted-saxbuilder-xxe-spring
        saxBuilder.build(reader);
    }

    @RequestMapping("/hello3")
    public void doPost3(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder(XMLReaders.DTDVALIDATING);
        // ok: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello4")
    public void doPost4(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        // ruleid: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello5")
    public void doPost5(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        // ok: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello6")
    public void doPost6(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        // ruleid: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello7")
    public void doPost7(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        // ok: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello8")
    public void doPost8(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://xml.org/sax/features/external-general-entities", false);
        // ruleid: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello9")
    public void doPost9(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ruleid: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello10")
    public void doPost10(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://xml.org/sax/features/external-general-entities", false);
        saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ok: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello11")
    public void doPost11(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setExpandEntities(false);
        saxBuilder.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // ok: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello12")
    public void doPost12(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/xinclude", false);
        // ruleid: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello13")
    public void doPost13(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        // ruleid: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }

    @RequestMapping("/hello14")
    public void doPost14(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File input = new File(file);
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setExpandEntities(false);
        // ruleid: tainted-saxbuilder-xxe-spring
        saxBuilder.build(input);
    }
}
