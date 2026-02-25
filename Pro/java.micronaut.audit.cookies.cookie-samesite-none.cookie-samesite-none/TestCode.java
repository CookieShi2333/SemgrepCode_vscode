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
    // ok: cookie-samesite-none
    SimpleCookie s = new SimpleCookie("foo", "bar");

    // ok: cookie-samesite-none
    Cookie ccc = Cookie.of("foo", "bar");

    // ok: cookie-samesite-none
    return HttpResponse.ok().cookie(Cookie.of("foo", "bar").sameSite(SameSite.Lax));
  }

  @Post("/test2")
  public MutableHttpMessage<Object> postTest2() throws FileNotFoundException {
    Cookie c1 = new SimpleCookie("foo", "bar");
    // ok: cookie-samesite-none
    c1.sameSite(SameSite.Lax);

    Cookie c2 = new NettyCookie("foo", "bar");
    // ruleid: cookie-samesite-none
    c2.sameSite(SameSite.None);

    // ok: cookie-samesite-none
    NettyCookie r1 = new NettyCookie("foo", "bar").sameSite(SameSite.Strict);
    // ruleid: cookie-samesite-none
    NettyCookie r2 = new NettyCookie("foo", "bar").sameSite(SameSite.None);

    SameSite COOKIE_PARAM = SameSite.None;
    // ruleid: cookie-samesite-none
    NettyCookie r2 = new NettyCookie("foo", "bar").sameSite(COOKIE_PARAM);

    // ruleid: cookie-samesite-none
    return HttpResponse.ok().cookie(Cookie.of("zzz", "ddd").sameSite(SameSite.None));
  }
}
