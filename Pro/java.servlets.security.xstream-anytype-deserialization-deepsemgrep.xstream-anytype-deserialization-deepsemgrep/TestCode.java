package example;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import org.xml.sax.InputSource;

class BadDocumentBuilderFactoryStatic {
    public doPost1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        String xml = req.getParameter("xml");

        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        // ruleid: xstream-anytype-deserialization-deepsemgrep
        Person newJoe = (Person)xstream.fromXML(xml);
    }

    public notARequest(String xml) throws ServletException, IOException  {
        XStream xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
        // ok: xstream-anytype-deserialization-deepsemgrep
        Person newJoe = (Person)xstream.fromXML(xml);
    }

    public doOkPost1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        String xml = req.getParameter("xml");

        XStream xstream = new XStream();
        xstream.alias("person", Person.class);
        xstream.allowTypeHierarchy(Person.class);

        // ok: xstream-anytype-deserialization-deepsemgrep
        Person newJoe = (Person)xstream.fromXML(xml);
    }

    public doOkPost1(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        String xml = "<hardcoded>value</hardcoded>";

        XStream xstream = new XStream();
        xstream.alias("person", Person.class);
        xstream.allowTypeHierarchy(Person.class);

        // ok: xstream-anytype-deserialization-deepsemgrep
        Person newJoe = (Person)xstream.fromXML(xml);
    }

}
