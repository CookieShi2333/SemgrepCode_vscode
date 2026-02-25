package parsers.XMLReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.*;
// import org.xml.sax.helpers.XMLReaderFactory;

class FoobarIfs {

    private static XMLReader staticReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();

    static {
        staticReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    }

    @RequestMapping("/foo0")
    public void example0(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
      File f = new File(file);
      XMLReader reader;

      if( saxParser == null ){
          reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      } else {
          reader = saxParser.getXMLReader();
      }

      // ruleid: tainted-xmlreader-xxe-spring
      reader.parse(new InputSource(new InputStream(file)));
    }

    @RequestMapping("/foo1")
    public void example1(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File f = new File(file);
        InputSource is = new InputSource(new InputStream(file));

        XMLReader reader = null;

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } else {
            reader = saxParser.getXMLReader();
        }

        // ruleid: tainted-xmlreader-xxe-spring
        reader.parse(is);
    }

    @RequestMapping("/foo2")
    public void example2(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File f = new File(file);
        InputSource is = new InputSource(new InputStream(file));

        XMLReader reader = null;

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        } else {
            reader = saxParser.getXMLReader();
        }

        // ruleid: tainted-xmlreader-xxe-spring
        reader.parse(is);

    }

    @RequestMapping("/foo3")
    public void example3(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File f = new File(file);
        InputSource is = new InputSource(new InputStream(file));

        XMLReader reader = null;

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } else {
            reader = saxParser.getXMLReader();
            reader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        }

        // ruleid: tainted-xmlreader-xxe-spring
        reader.parse(is);
    }

    @RequestMapping("/foo4")
    public void example4(@RequestParam(defaultValue="test.txt") String file, @RequestParam String example) {
        File f = new File(file);
        InputSource is = new InputSource(new InputStream(file));

        XMLReader reader = null;

        if( saxParser == null ){
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        } else {
            reader = saxParser.getXMLReader();
        }

        // ok: tainted-xmlreader-xxe-spring
        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        reader.parse(is);
    }
}

class Foobar {
    private SAXParser saxParser;

    @RequestMapping("/hello1")
    public void doPost1(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      this.parser = SAXParserFactory.newInstance().newSAXParser();
      SAXParser saxParser = this.parser.newSAXParser();
      XMLReader reader = saxParser.getXMLReader();
      // ruleid: tainted-xmlreader-xxe-spring
      reader.parse(is);
    }

    @RequestMapping("/hello2")
    public void doPost2(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      // ruleid: tainted-xmlreader-xxe-spring
      reader.parse(is);
    }

    @RequestMapping("/hello3")
    public void doPost3(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
      // ruleid: tainted-xmlreader-xxe-spring
      reader.parse(is);
    }

    @RequestMapping("/hello4")
    public void doPost4(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      // ok: tainted-xmlreader-xxe-spring
      reader.parse(is);
    }

    @RequestMapping("/hello5")
    public void doPost5(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
      // ok: tainted-xmlreader-xxe-spring
      reader.parse(is);
    }

    @RequestMapping("/hello6")
    public void doPost6(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
      // ok: tainted-xmlreader-xxe-spring
      reader.parse(is);
    }

    @RequestMapping("/hello7")
    public void doPost7(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      // ok: tainted-xmlreader-xxe-spring
      reader.parse(is);
    }

    @RequestMapping("/hello8")
    public void doPost8(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      try {
        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        //ruleid: tainted-xmlreader-xxe-spring
        reader.parse(is);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    @RequestMapping("/hello9")
    public void doPost9(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      try {
        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        //ruleid: tainted-xmlreader-xxe-spring
        reader.parse(is);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }


    @RequestMapping("/hello10")
    public void doPost10(@RequestParam(defaultValue="test.txt") String f, @RequestParam String example) {
      File file = new File(f);
      InputSource is = new InputSource(new InputStream(file));
      try {
        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        //ok: tainted-xmlreader-xxe-spring
        reader.parse(is);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
}
