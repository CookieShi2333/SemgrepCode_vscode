package org.testcode;

import java.util.UUID;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
import org.apache.http.client.methods.HttpGet;

public class Test {
    @Bean
    public ResponseSpec getWebClient1(@RequestParam String uri)
    {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                        .doOnConnected(conn -> conn
                                .addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10))));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        String url = "https://localhost:3000";
        return WebClient.builder()
                // ok: tainted-ssrf-spring-add
                .baseUrl(url)
                .build().retrieve();
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void test1(@RequestParam String param) {
        String url = "https://" + param;

        // ruleid: tainted-ssrf-spring-add
        new URL(url).openConnection();
        // ruleid: tainted-ssrf-spring-add
        new URL(url).openStream();

        URL url = new URL(url);
        // ruleid: tainted-ssrf-spring-add
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String body = reader.lines().collect(Collectors.joining());
        return body;
    }

    @GetMapping("/api/foos")
    @ResponseBody
    protected void test2(@RequestParam String uri, @RequestParam String test) {
        org.apache.http.HttpClient client = new org.apache.http.HttpClient();
        URI uri = new URI(uri);
        // ruleid: tainted-ssrf-spring-add
        org.apache.http.HttpRequest r = org.apache.http.HttpRequest.newBuilder(uri).build();
        client.send(r, null);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    protected void test3(@RequestParam String host, @RequestParam String test)  {
        ObjectMapper mapper = new ObjectMapper();

        String url = "https://" + host + "/path";
        try (HttpClient client=HttpClients.createDefault()) {
            // ruleid: tainted-ssrf-spring-add
            HttpGet request = new HttpGet(url);

            APOD response = client.execute(request, httpResponse ->
                mapper.readValue(httpResponse.getEntity().getContent(), APOD.class));

            System.out.println(response.title);
        }
    }

    @GetMapping("/api/foos")
    @ResponseBody
    protected void test4(@RequestParam String host, @RequestParam String test) {
        ObjectMapper mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.google.com";
        Request request = new Request.Builder()
        // ok: tainted-ssrf-spring-add
           .url(url)
           .build(); // defaults to GET

        Response response = client.newCall(request).execute();

        APOD apod = mapper.readValue(response.body().byteStream(), APOD.class);

        System.out.println(apod.title);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    protected void test5(@RequestParam String host, @RequestParam String test) {
        ObjectMapper mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();
        String url = "https://" + host + "/path";
        Request request = new Request.Builder()
        // ruleid: tainted-ssrf-spring-add
           .url(url)
           .build(); // defaults to GET

        Response response = client.newCall(request).execute();

        APOD apod = mapper.readValue(response.body().byteStream(), APOD.class);

        System.out.println(apod.title);
    }

    @GetMapping("/tweets-blocking")
    public List<Tweet> test5(@RequestParam String uri) {
        log.info("Starting BLOCKING Controller!");
        final String uri = uri;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Tweet>> response = restTemplate.exchange(
        // ruleid: tainted-ssrf-spring-add
        uri, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Tweet>>(){});

        List<Tweet> result = response.getBody();
        result.forEach(tweet -> log.info(tweet.toString()));
        log.info("Exiting BLOCKING Controller!");
        return result;
    }

    @GetMapping(value = "/tweets-non-blocking",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> test6(@RequestParam String uri) {
        log.info("Starting NON-BLOCKING Controller!");
        Flux<Tweet> tweetFlux = WebClient.create()
        .get()
        // ruleid: tainted-ssrf-spring-add
        .uri(uri)
        .retrieve()
        .bodyToFlux(Tweet.class);

        tweetFlux.subscribe(tweet -> log.info(tweet.toString()));
        log.info("Exiting NON-BLOCKING Controller!");
        return tweetFlux;
    }

    @GetMapping(value = "/tweets-non-blocking",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> test6(@RequestParam String test) {
        WebClient client = WebClient.create();

        WebClient.ResponseSpec responseSpec = client.get()
            // ok: tainted-ssrf-spring-add
            .uri("https://www.google.com")
            .retrieve();
    }

    @GetMapping(value = "/tweets-non-blocking",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> test6(@RequestParam String test) {
        WebClient client = WebClient.create();

        WebClient.ResponseSpec responseSpec = client.get()
            // ok: tainted-ssrf-spring-add
            .uri("https://www.google.com/{test}")
            .retrieve();
    }

    @GetMapping(value = "/tweets-non-blocking",
    produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> test6b(@RequestParam String test) {
        String url = "http://" + test;

        WebClient.ResponseSpec responseSpec = WebClient.builder()
        // ruleid: tainted-ssrf-spring-add
        .baseUrl(url)
        .defaultCookie("cookieKey", "cookieValue")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
        .build()
        .get()
        .retrieve();
    }

    @GetMapping("user/{id:\\d+}")
    public User getUser(@PathVariable Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        // ok: tainted-ssrf-spring-add
        URI uri = UriComponentsBuilder.fromUriString("http://Server-Provider/user/{id}")
                .build().expand(params).encode().toUri();
        return this.restTemplate.getForEntity(uri, User.class).getBody();
    }

    @GetMapping("hello/{message}")
    public String hello(@PathVariable String message) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider");
        String path = String.format("http://%s:%s/provide/%s", serviceInstance.getHost(), serviceInstance.getPort(), message);
        // ok: tainted-ssrf-spring-add
        String result = restTemplate.getForObject(path, String.class);
        return String.format("%s from %s %s", result, serviceInstance.getHost(), serviceInstance.getPort());
    }

    @GetMapping("/echo-rest/{str}")
    public String rest(@PathVariable String str) {
        String SERVICE_PROVIDER_ADDRESS = "http://service-provider";
        // ok: tainted-ssrf-spring-add
        return urlCleanedRestTemplate
                .getForObject(SERVICE_PROVIDER_ADDRESS + "/echo/" + str,
                        String.class);
    }

    @GetMapping("/get_user")
    @HystrixCommand(fallbackMethod = "getUserFallback")
    public String getUser(@RequestParam("id") Integer id) {
        logger.info("[getUser][准备调用 user-service 获取用户({})详情]", id);
        // ok: tainted-ssrf-spring-add
        return restTemplate.getForEntity("http://127.0.0.1:18080/user/get?id=" + id, String.class).getBody();
    }

    @GetMapping("/{id}")
    public CommonResult getUser(@PathVariable Long id) {
        // ok: tainted-ssrf-spring-add
        return restTemplate.getForObject(userServiceUrl + "/user/{1}", CommonResult.class, id);
    }

    @GetMapping("/test")
    public static List<String> getLinks(@RequestParam String url) throws IOException {
        List<String> result = new ArrayList<String>();
        // ruleid: tainted-ssrf-spring-add
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a");
        for (Element link : links) {
          result.add(link.absUrl("href"));
        }
        return result;
    }

    private void testing (@RequestParam String host) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            String exchangeURL = "http://" + host;
            new RestTemplate()
                .exchange(
                    // ruleid: tainted-ssrf-spring-add
                    exchangeURL,
                    HttpMethod.GET,
                    httpEntity,
                    Void.class);
        } catch (Exception e) {
        // don't care
        }
    }

    @GetMapping("/api/foos")
    @ResponseBody
    protected void test7(@RequestParam String tainted, @RequestParam String test) {
        ObjectMapper mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();
        String url = myURL.newBuilder()
            .addQueryParameter("p", tainted)
            .build();
        Request request = new Request.Builder()
        // ok: tainted-ssrf-spring-add
           .url(url)
           .build(); // defaults to GET

        Response response = client.newCall(request).execute();

        APOD apod = mapper.readValue(response.body().byteStream(), APOD.class);

        System.out.println(apod.title);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    protected void test4(@RequestParam UUID OrgID) {
        org.apache.http.HttpClient client = new org.apache.http.HttpClient();
        URI uri = new URI(OrgID);
        // ok: tainted-ssrf-spring-add
        org.apache.http.HttpRequest r = org.apache.http.HttpRequest.newBuilder(uri).build();
        client.send(r, null);
    }
}
