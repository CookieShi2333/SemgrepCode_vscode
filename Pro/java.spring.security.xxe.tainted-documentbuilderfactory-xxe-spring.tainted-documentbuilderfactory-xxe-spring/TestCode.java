package org.example;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;

import java.io.InputStream;

import org.xml.sax.InputSource;

class BadDocumentBuilderFactoryStatic {
    @RequestMapping(path="/message", produces=MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public void doPost1(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // ruleid: tainted-documentbuilderfactory-xxe-spring
        db.parse(new InputSource(file));
    }

    @RequestMapping("/hello")
    public void doPost2(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new FileInputStream(file);
        //ruleid: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }

    @RequestMapping("/foo")
    public void doPost3(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ok: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }

    @RequestMapping("/hello2")
    public void doPost4(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ok: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }

    @RequestMapping("/hello3")
    public void doPost5(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ok: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }

    @RequestMapping("/hello4")
    public void doPost6(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // general entities is missing
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ruleid: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }

    @RequestMapping("/hello5")
    public void doPost7(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ok: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }


    @RequestMapping("/hello6")
    public void doPost8(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ruleid: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }


    @RequestMapping("/hello7")
    public void doPost9(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ruleid: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }

    @RequestMapping("/hello8")
    public void doPost10(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ok: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }

    @RequestMapping("/hello9")
    public void doPost11(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setExpandEntityReferences(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ruleid: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }

    @RequestMapping("/hello10")
    public void doPost12(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setXIncludeAware(false);
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //ruleid: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }

    @RequestMapping("/hello11")
    public void doPost13(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setEntityResolver(new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                return new InputSource(new StringReader(""));
            }
        });

        InputStream is = new FileInputStream(file);
        //ok: tainted-documentbuilderfactory-xxe-spring
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

    @RequestMapping("/hello12")
    public void doPost14(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        DocumentBuilder db = dbf.newDocumentBuilder();

        InputStream is = new FileInputStream(file);
        //todook: tainted-documentbuilderfactory-xxe-spring
        db.parse(is);
    }
}
