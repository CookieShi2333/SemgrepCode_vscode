package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;

@Controller("/hello")
public class HelloController {

  @Get("/ok-redir1")
  public HttpResponse okRedir1(@QueryValue String name) throws URISyntaxException {
    // ok: open-redirect
    return HttpResponse.redirect(new URI("/my-path/" + name));
  }

  @Get("/redir2")
  public HttpResponse redir2(@QueryValue String name) throws URISyntaxException {
    // ruleid: open-redirect
    return HttpResponse.permanentRedirect(new URI(name + "/foobar"));
  }

  @Get("/redir3")
  public HttpResponse redir3(@QueryValue String location) throws URISyntaxException {
    // ruleid: open-redirect
    return HttpResponse.temporaryRedirect(new URI(location));
  }

  @Get("/redir4")
  public HttpResponse redir4(@QueryValue String location) throws URISyntaxException {
    // ruleid: open-redirect
    return HttpResponse.ok("Redirecting...").header("Location", location);
  }

  @Get("/redir5")
  public HttpResponse redir5() throws URISyntaxException {
    // ok: open-redirect
    return HttpResponse.ok("Redirecting...").header("Location", "www.example.com");
  }

}
