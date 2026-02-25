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

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.mapping.Mapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;

import java.io.*;

@RestController
public class GreetingController {

  @GetMapping("/test1")
  public SomeClass test1(@RequestBody String objectBin, HttpServletResponse response) {
    ByteArrayInputStream isr = new ByteArrayInputStream(objectBin.getBytes());
    Unmarshaller un = new Unmarshaller();
    // ruleid: castor-deserialization-deepsemgrep
    SomeClass object = (SomeClass)un.unmarshal(isr);
    return object;
  }

  @GetMapping("/ok-test1")
  public SomeClass okTest(@RequestBody String person, HttpServletResponse response) {
    ByteArrayInputStream isr = new ByteArrayInputStream("hardcoded value");
    Unmarshaller un = new Unmarshaller();
    // ok: castor-deserialization-deepsemgrep
    SomeClass object = (SomeClass)un.unmarshal(isr);
    return object;
  }

  public SomeClass notARequest(String objectBin) throws IOException  {
    ByteArrayInputStream isr = new ByteArrayInputStream(objectBin.getBytes());
    Unmarshaller un = new Unmarshaller();
    // ok: castor-deserialization-deepsemgrep
    SomeClass object = (SomeClass)un.unmarshal(isr);
    return object;
  }

}
