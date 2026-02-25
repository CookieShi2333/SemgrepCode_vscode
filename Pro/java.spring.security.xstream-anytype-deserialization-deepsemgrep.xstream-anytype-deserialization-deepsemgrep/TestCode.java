package login.java.spring.security;

import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;

@RestController
public class GreetingController {

  @GetMapping("/test1")
  public Person test1(@RequestBody String person, HttpServletResponse response) {
    XStream xstream = new XStream();
    xstream.addPermission(AnyTypePermission.ANY);
    // ruleid: xstream-anytype-deserialization-deepsemgrep
    Person newJoe = (Person)xstream.fromXML(person);
    return newJoe;
  }

  @GetMapping("/test2")
  public Person okTest1(@RequestBody String person, HttpServletResponse response) {
    XStream xstream = new XStream();
    xstream.alias("person", Person.class);
    xstream.allowTypeHierarchy(Person.class);
    // ok: xstream-anytype-deserialization-deepsemgrep
    Person newJoe = (Person)xstream.fromXML(person);
    return newJoe;
  }

  @GetMapping("/test3")
  public Person okTest1(@RequestBody String person, HttpServletResponse response) {
    XStream xstream = new XStream();
    xstream.addPermission(AnyTypePermission.ANY);
    // ok: xstream-anytype-deserialization-deepsemgrep
    Person newJoe = (Person)xstream.fromXML("hardcoded value");
    return newJoe;
  }

  public notARequest(String xml) throws ServletException, IOException  {
    XStream xstream = new XStream();
    xstream.addPermission(AnyTypePermission.ANY);
    // ok: xstream-anytype-deserialization-deepsemgrep
    Person newJoe = (Person)xstream.fromXML(xml);
  }

}
