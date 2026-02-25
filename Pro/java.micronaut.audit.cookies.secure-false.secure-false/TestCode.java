package com.example;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.http.cookie.SameSite;
import io.micronaut.http.netty.cookies.NettyCookie;
import io.micronaut.http.server.cors.CrossOrigin;
import io.micronaut.http.simple.cookies.SimpleCookie;
import io.micronaut.http.simple.cookies.SimpleCookieFactory;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;

import java.io.*;


@Controller("/hello")
public class HelloController {

  @Post("/test1")
  public MutableHttpMessage<Object> postTest1() throws FileNotFoundException {
    // ok: secure-false
    SimpleCookie s = new SimpleCookie("foo", "bar");

    // ok: secure-false
    Cookie ccc = Cookie.of("foo", "bar");

    // ok: secure-false
    return HttpResponse.ok().cookie(Cookie.of("foo", "bar").secure(true));
  }

  @Post("/test2")
  public MutableHttpMessage<Object> postTest2() throws FileNotFoundException {
    Cookie c1 = new SimpleCookie("foo", "bar");
    // ok: secure-false
    c1.secure(true);

    Cookie c2 = new NettyCookie("foo", "bar");
    // ruleid: secure-false
    c2.secure(false);

    // ok: secure-false
    NettyCookie r1 = new NettyCookie("foo", "bar").secure(true);
    // ruleid: secure-false
    NettyCookie r2 = new NettyCookie("foo", "bar").secure(false);

    Boolean SECURE_COOKIE = false;
    // ruleid: secure-false
    NettyCookie r2 = new NettyCookie("foo", "bar").secure(SECURE_COOKIE);

    // ruleid: secure-false
    return HttpResponse.ok().cookie(Cookie.of("zzz", "ddd").secure(false));
  }
}
