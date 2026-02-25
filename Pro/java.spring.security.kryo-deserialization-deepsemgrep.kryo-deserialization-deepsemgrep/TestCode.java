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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

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
    Kryo kryo = new Kryo();
    kryo.setRegistrationRequired(false);

    Input input = new Input(new ByteArrayInputStream(objectBin.getBytes()));
    // ruleid: kryo-deserialization-deepsemgrep
    SomeClass object2 = kryo.readObject(input, SomeClass.class);
    input.close();
    return object2;
  }

  @GetMapping("/test2")
  public SomeClass test2(@RequestBody String objectBin, HttpServletResponse response) {
    Kryo kryo = new Kryo();

    Input input = new Input(new ByteArrayInputStream(objectBin.getBytes()));
    // ruleid: kryo-deserialization-deepsemgrep
    SomeClass object2 = (SomeClass)kryo.readClassAndObject(input);
    input.close();
    return object2;
  }

  @GetMapping("/ok-test1")
  public SomeClass okTest(@RequestBody String person, HttpServletResponse response) {
    Kryo kryo = new Kryo();

    Input input = new Input(new ByteArrayInputStream("hardcoded value"));
    // ok: kryo-deserialization-deepsemgrep
    SomeClass object2 = (SomeClass)kryo.readClassAndObject(input);
    input.close();
    return object2;
  }

  public notARequest(String objectBin) throws IOException  {
    Kryo kryo = new Kryo();

    Input input = new Input(new ByteArrayInputStream(objectBin));
    // ok: kryo-deserialization-deepsemgrep
    SomeClass object2 = (SomeClass)kryo.readClassAndObject(input);
    input.close();
  }

}
