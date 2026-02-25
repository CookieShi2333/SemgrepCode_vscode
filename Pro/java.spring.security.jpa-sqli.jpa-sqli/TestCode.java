package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@RestController
public class GreetingController {

  private static final EntityManagerFactory emf;

  static {
      emf = Persistence.createEntityManagerFactory("com.example.movie_catalog");
  }

  public static EntityManager getEntityManager() {
      return emf.createEntityManager();
  }

  @GetMapping("/test1")
  public UserEntity test1(@RequestParam(value = "name", defaultValue = "World") String name, HttpServletResponse response) {
    EntityManager em = HibernateOperations.getEntityManager();
    // ruleid: jpa-sqli
    TypedQuery<UserEntity> q = em.createQuery("select * from Users where name = '" + name + "'", UserEntity.class);
    UserEntity res = q.getSingleResult();
    return res;
  }

  @GetMapping("/test2")
  public String test2(@RequestBody String name, HttpServletResponse response) {
    EntityManager em = HibernateOperations.getEntityManager();
    String sql = String.format("select * from Users where user = 'admin' and password='%s'", name);
    // ruleid:jpa-sqli
    em.createNativeQuery(sql);
    return "ok";
  }

  @GetMapping("/ok-test1/{name}")
  public String okTest1(@PathVariable String name) {
    EntityManager em = HibernateOperations.getEntityManager();
    // ok: jpa-sqli
    TypedQuery<UserEntity> q = em.createQuery("select * from Users where name = 'name'", UserEntity.class);
    UserEntity res = q.getSingleResult();
    return res;
  }

  public String okTest2(EntityManager em, String name) {
    String sql = String.format("select * from Users where user = 'admin' and password='%s'", name);
    // ok:jpa-sqli
    em.createNativeQuery(sql);
    return "ok";
  }

  @GetMapping("/okTest3")
  public String okTest3(@RequestBody Integer name, HttpServletResponse response) {
    EntityManager em = HibernateOperations.getEntityManager();
    String sql = String.format("select * from Users where user = 'admin' and password='%s'", name);
    // ok:jpa-sqli
    em.createNativeQuery(sql);
    return "ok";
  }

  @GetMapping("/okTest4")
  public String okTest4(@RequestBody String name, HttpServletResponse response) {
    EntityManager em = HibernateOperations.getEntityManager();
    String sql = String.format("select * from Users where user = 'admin' and password='%s'", (name != null));
    // ok:jpa-sqli
    em.createNativeQuery(sql);
    return "ok";
  }
}
