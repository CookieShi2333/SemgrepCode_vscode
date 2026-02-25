package com.example;

import java.util.concurrent.Flow.Publisher;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.netty.DefaultHttpClient;
import jakarta.inject.Inject;

@Controller("/hello")
public class HelloController {

  @Inject
  @Client("/")
  private HttpClient client;

  @Get("/client3")
  Publisher<String> test3(String uri) {
    // ruleid: httpclient-taint-concat
    return this.client.retrieve(new URL("https://" + uri).toString());
  }

  @Get("/test4{?cmd*}")
  public Publisher<List<GithubRelease>> test4(Foobar cmd) {
    HttpRequest<?> req = HttpRequest.GET("http://" + cmd.name + "/path")
            .header(USER_AGENT, "Micronaut HTTP Client")
            .header(ACCEPT, "application/vnd.github.v3+json, application/json");
    // ruleid: httpclient-taint-concat
    return client.retrieve(req, Argument.listOf(GithubRelease.class));
  }

  @Post("/test5")
  public Publisher<String> test5(HttpRequest<?> request) {
    Foobar cmd = request.getBody(Foobar.class).orElse(null);
    HttpRequest<?> req = HttpRequest.GET("http://" + cmd + "?query=1")
            .header(USER_AGENT, "Micronaut HTTP Client")
            .header(ACCEPT, "application/vnd.github.v3+json, application/json");
    // ruleid: httpclient-taint-concat
    return client.retrieve(req, Argument.listOf(GithubRelease.class));
  }

  @Get("/client6")
  Publisher<String> test6(Foobar cmd) {
    DefaultHttpClient dclient = new DefaultHttpClient();
    // ruleid: httpclient-taint-concat
    return dclient.retrieve(String.format("https://%s", cmd.url));
  }

  @Get("/client7")
  Publisher<String> test7(Foobar cmd) {
    // ruleid: httpclient-taint-concat
    DefaultHttpClient dclient = new DefaultHttpClient(String.format("https://%s", cmd.url));
    return dclient.retrieve("/foobar");
  }

  @Get("/ok-test1")
  Publisher<String> okTest1(Integer uriNum) {
    // ok: httpclient-taint-concat
    return this.client.retrieve(new URL("https://" + uriNum + ".path.com/yo").toString());
  }

  @Get("/ok-test2{?cmd*}")
  public Publisher<List<GithubRelease>> okTest2(Foobar cmd) {
    HttpRequest<?> req = HttpRequest.GET("http://www.google.com/" + cmd.name + "/path")
            .header(USER_AGENT, "Micronaut HTTP Client")
            .header(ACCEPT, "application/vnd.github.v3+json, application/json");
    // ok: httpclient-taint-concat
    return client.retrieve(req, Argument.listOf(GithubRelease.class));
  }

  @Get("/ok-test3")
  Publisher<String> okTest3(Foobar cmd) {
    // ok: httpclient-taint-concat
    return client.retrieve(String.format("https://www.google.com/%s", cmd.url));
  }

  @Get("/ok-test4")
  Publisher<String> okTest4(Foobar cmd) {
    // ok: httpclient-taint-concat
    return client.retrieve(String.format("https://www.google.com/%s", "my-path"));
  }

  @Get("/ok-client5")
  public Publisher<String> okTest5(Foobar cmd) {
    // we going to catch this case with a different rule:
    // ok: httpclient-taint-concat
    return client.retrieve(cmd.name);
  }

  public Mono<Object> okPostHandler(Object requestBody, String accessToken, HttpRequest<?> incomingRequest) {
    UriBuilder builder = UriBuilder.of(apigwUrl).path(incomingRequest.getPath().replace("/foo/bar", ""));
    HttpRequest<?> request = HttpRequest
      .POST(builder.build(), requestBody)
      .header(ACCEPT, "application/json")
      .bearerAuth(accessToken);
    // ok: httpclient-taint-concat
    return Mono.from(httpClient.retrieve(request, Object.class));
  }

  @Get("/ok-test6")
  Publisher<String> okTest6(Foobar cmd) {
    // ok: httpclient-taint-concat
    return client.retrieve(HttpRequest.POST("https://www.foobar.com", Map.of("data", cmd.data)).contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE), Map.class);
  }

}
