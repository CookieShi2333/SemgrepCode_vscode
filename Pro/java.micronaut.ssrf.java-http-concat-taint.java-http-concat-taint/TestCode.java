package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import java.net.URL;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.jsoup.Jsoup;

@Controller("/hello")
public class HelloController {

  @Get("/client1")
  String test1(String uri) {
    HttpRequest req = HttpRequest.newBuilder().uri(new URI("https://" + uri)).GET().build();
    HttpClient cl = HttpClient.newBuilder().build();
    // ruleid: java-http-concat-taint
    HttpResponse<String> res = cl.send(req, HttpResponse.BodyHandlers.ofString());
    return "ok";
  }

  @Get("/client2")
  String test2(String uri) {
    HttpRequest req = HttpRequest.newBuilder().uri(new URI("https://" + uri)).GET().build();
    // ruleid: java-http-concat-taint
    return HttpClient.newHttpClient().sendAsync(req, HttpResponse.BodyHandlers.ofString());
  }

  @Get("/ok-client3")
  String okTest3(String uri) {
    HttpRequest req = HttpRequest.newBuilder()
      .uri(new URI("https://www.example.com"))
      .headers("Foobar", "https://" + uri)
      .GET()
      .build();
    // ok: java-http-concat-taint
    return HttpClient.newHttpClient().sendAsync(req, HttpResponse.BodyHandlers.ofString());
  }

  @Get("/client4")
  String test4(String param) {
    String url = String.format("https://%s", param);

    // ruleid: java-http-concat-taint
    new URL(url).openConnection();
    // ruleid: java-http-concat-taint
    new URL(url).openStream();

    URL url = new URL(url);
    // ruleid: java-http-concat-taint
    URLConnection connection = url.openConnection();
    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String body = reader.lines().collect(Collectors.joining());
    return body;
  }

  @Get("/ok-client5")
  String okTest3(String param) {
    String url = String.format("https://www.example.com/%s", param);

    // ok: java-http-concat-taint
    URL url = new URL(url);
    URLConnection connection = url.openConnection();
    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String body = reader.lines().collect(Collectors.joining());
    return body;
  }

  @Get("/client6")
  String test6(String url) {
    RestTemplate restTemplate = new RestTemplate();
    // ruleid: java-http-concat-taint
    ResponseEntity<List<Tweet>> response = restTemplate.exchange("https://" + url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Tweet>>(){});

    String result = response.getBody();
    return result;
  }

  @Get("/ok-client7")
  String test7(String method) {
    RestTemplate restTemplate = new RestTemplate();
    // ok: java-http-concat-taint
    ResponseEntity<List<Tweet>> response = restTemplate.exchange("https://www.example.com", method, null, new ParameterizedTypeReference<List<Tweet>>(){});

    String result = response.getBody();
    return result;
  }

  @Get("/client8")
  String test8(String foo) {
    String url = String.format("https://%s", foo.fullUri);

    Flux<Tweet> tweetFlux = WebClient.create()
      .get()
      // ruleid: java-http-concat-taint
      .uri(url)
      .retrieve()
      .bodyToFlux(Tweet.class);

    tweetFlux.subscribe(tweet -> log.info(tweet.toString()));
    return tweetFlux;
  }

  @Get("/ok-client9")
  String test9(Foobar foo) {
    WebClient client = WebClient.create();

    WebClient.ResponseSpec responseSpec = client.get()
      // ok: java-http-concat-taint
      .uri("https://www.google.com")
      .retrieve();
    return "ok"
  }

  @Get("/client10")
  String test10(String foo) {
    String url = String.format("https://%s", foo.fullUri);

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
    // ruleid: java-http-concat-taint
        .url(url)
        .build();

    Response response = client.newCall(request).execute();
    doSMth(response);
    return "ok";
  }

  @Get("/ok-client11")
  String test11(Foobar foo) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
    // ok: java-http-concat-taint
        .url("http://www.example.com")
        .build();

    Response response = client.newCall(request).execute();
    doSMth(response);
    return "ok";
  }

  @Get("/client12")
  String test12(String foo) {
    String url = String.format("https://%s", foo.fullUri);
    List<String> result = new ArrayList<String>();
    // ruleid: java-http-concat-taint
    Document doc = Jsoup.connect(url).get();
    Elements links = doc.select("a");
    for (Element link : links) {
      result.add(link.absUrl("href"));
    }
    doSmth(result);
    return "ok";
  }

  @Get("/ok-client13")
  String test13(Foobar foo) {
    List<String> result = new ArrayList<String>();
    // ok: java-http-concat-taint
    Document doc = Jsoup.connect("https://www.example.com").get();
    Elements links = doc.select("a");
    for (Element link : links) {
      result.add(link.absUrl("href"));
    }
    doSmth(result);
    return "ok";
  }

  @Get("/client14")
  String test14(String foo) {

    org.apache.http.client.HttpClient client = new HttpClient();
    String formatUrl = String.format("https://%s", foo.fullUri);
    URI uri = new URI(formatUrl);
    // ruleid: java-http-concat-taint
    org.apache.http.HttpRequest result = org.apache.http.HttpRequest.newBuilder(uri).build();
    client.send(r, null);

    doSmth(result);
    return "ok";
  }

  @Get("/ok-client15")
  String okTest15(Foobar foo) {

    org.apache.http.client.HttpClient client = new HttpClient();
    String uri = "http://www.example.com";
    // ok: java-http-concat-taint
    org.apache.http.HttpRequest result = org.apache.http.HttpRequest.newBuilder(uri).build();
    client.send(r, null);

    doSmth(result);
    return "ok";
  }

}
