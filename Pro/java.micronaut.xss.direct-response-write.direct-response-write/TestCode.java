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
import io.micronaut.views.View;
import io.micronaut.views.ModelAndView;

@Controller("/hello")
public class HelloController {

  @Secured(SecurityRule.IS_ANONYMOUS)
  @Get(value = "/test1", produces = MediaType.TEXT_HTML)
  public String test1(@QueryValue String name) {
    if (authenticated()) {
      return """
          <!DOCTYPE html>
          <body>
          <h2>username: Anonymous</h2>
          <nav>
              <ul>
                  <li><a href="/oauth/login">Enter</a></li>
              </ul>
          </nav>
          </body>
          </html>
      """;
    }
    // ruleid: direct-response-write
    return """
        <!DOCTYPE html>
        <body>
        <h2>username: %s</h2>
        <nav>
            <ul>
                <li><a href="/logout">Logout</a></li>
            </ul>
        </nav>
        </body>
        </html>
    """.formatted(name);
  }

  @Get(value = "/test2")
  @Produces(MediaType.TEXT_HTML)
  public HttpResponse<String> test2(@QueryValue String name) {
    // ruleid: direct-response-write
    return HttpResponse.ok("<div>" + name + "</div>");
  }

  @Get(value = "/ok-test3")
  @View("test.html")
  @Produces(MediaType.TEXT_HTML)
  public HttpResponse<String> okTest3(@QueryValue String name) {
    // ok: direct-response-write
    return produceTemplate("<div>" + name + "</div>");
  }

  @Get(value = "/ok-test4")
  @Produces(MediaType.TEXT_HTML)
  public HttpResponse<String> okTest4(@QueryValue String name) {
    // ok: direct-response-write
    return new ModelAndView("home", new Person("name", name));
  }

  @Get(value = "/ok-test5")
  @Produces(MediaType.TEXT_HTML)
  public HttpResponse<String> okTest5(@QueryValue String name) {
    doSmth(name);
    // ok: direct-response-write
    return HttpResponse.ok("<div>" + foobar() + "</div>");
  }
}
