package test;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.beans.XMLDecoder;

class Test {
    @GetMapping("/api/bad1")
    @ResponseBody
    public String bad1(@RequestParam String input) {
        //ruleid: spring-tainted-xmldecoder
        XMLDecoder d = new XMLDecoder("<safe>" + input + "</safe>");
        try {
            Object result = d.readObject();
            return result;
        }
        finally {
            d.close();
        }
    }

    @GetMapping("/api/bad2")
    @ResponseBody
    public String bad2(@RequestParam byte[] input) {
        // ruleid: spring-tainted-xmldecoder
        XMLDecoder decoder = new XMLDecoder ( new ByteArrayInputStream(input));
		Object object = decoder.readObject();
		System.out.println(object.toString());
		decoder.close();
    }

    @GetMapping("/api/ok1")
    @ResponseBody
    public String ok1(Model model, @RequestParam String input) {
        //ok: spring-tainted-xmldecoder
        XMLDecoder d = new XMLDecoder("<safe>" + "input" + "</safe>");
        try {
            Object result = d.readObject();
            return result;
        }
        finally {
            d.close();
        }
    }

    @GetMapping("/api/ok2")
    @ResponseBody
    public void ok2(@RequestParam String input)
        throws ELException {
        File xmlFile = new File("demo.xml");
        FileInputStream fis = new FileInputStream(xmlFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        // ok: spring-tainted-xmldecoder
        XMLDecoder xmlDecoder = new XMLDecoder(bis);
        System.out.println(xmlDecoder.readObject());
        xmlDecoder.close();
    }
}
