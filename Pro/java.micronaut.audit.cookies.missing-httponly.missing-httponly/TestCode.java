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
import io.micronaut.http.HttpRequest;

import java.io.*;


@Controller("/hello")
public class HelloController {

  @Post("/test1")
  public MutableHttpMessage<Object> postTest1() throws FileNotFoundException {
    // ruleid: missing-httponly
    SimpleCookie s = new SimpleCookie("foo", "bar");

    // ok: missing-httponly
    Cookie c1 = getCookieSomewhere();

    // ok: missing-httponly
    return HttpResponse.ok().cookie(Cookie.of("foo", "bar").httpOnly(true));
  }

  @Post("/test2")
  public MutableHttpMessage<Object> postTest2(HttpRequest<?> request) throws FileNotFoundException {

    // ok: missing-httponly
    Cookie cookie = request.getCookies()
            .findCookie( "foobar" )
    // ruleid: missing-httponly
            .orElse( new NettyCookie( "foo", "bar" ) );

    if ( someVar != null )
    {
      cookie.value( someVar );
    }

    // ok: missing-httponly
    Cookie ccc = Cookie.of("zzz", "ddd");
    ccc.httpOnly(true).secure(true);

    // ruleid: missing-httponly
    Cookie cc2 = Cookie.of("zzz", "ddd");
    cc2.path("/").secure(true);

    // ok: missing-httponly
    Cookie c = new NettyCookie("foo", "bar");
    c.httpOnly(true);

    // ok: missing-httponly
    NettyCookie r = new NettyCookie("foo", "bar").httpOnly(true);

    // ruleid: missing-httponly
    Cookie z = new NettyCookie("foo", "bar");

    // ruleid: missing-httponly
    return HttpResponse.ok().cookie(Cookie.of("zzz", "ddd"));
  }
}
