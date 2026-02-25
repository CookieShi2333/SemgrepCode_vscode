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
    // ruleid: missing-secure
    SimpleCookie s = new SimpleCookie("foo", "bar");

    // ok: missing-secure
    Cookie c1 = getCookieSomewhere();

    // ok: missing-secure
    return HttpResponse.ok().cookie(Cookie.of("foo", "bar").secure(true));
  }

  @Post("/test2")
  public MutableHttpMessage<Object> postTest2(HttpRequest<?> request) throws FileNotFoundException {

    // ok: missing-secure
    Cookie cookie = request.getCookies()
            .findCookie( "foobar" )
    // ruleid: missing-secure
            .orElse( new NettyCookie( "foo", "bar" ) );

    if ( someVar != null )
    {
      cookie.value( someVar );
    }

    // ok: missing-secure
    Cookie c = new NettyCookie("foo", "bar");
    c.secure(true);

    // ok: missing-secure
    NettyCookie r = new NettyCookie("foo", "bar").secure(true);

    // ruleid: missing-secure
    Cookie z = new NettyCookie("foo", "bar");

    // ruleid: missing-secure
    return HttpResponse.ok().cookie(Cookie.of("zzz", "ddd"));
  }
}
