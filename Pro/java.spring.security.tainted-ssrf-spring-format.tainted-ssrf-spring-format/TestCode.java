package org.testcode;

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
                // ok: tainted-ssrf-spring-format
                .baseUrl(url)
                .build().retrieve();
    }

    @GetMapping("/api/foos")
    @ResponseBody
    public void test1(@RequestParam String param) {
        String url = String.format("https://%s", param);

        // ruleid: tainted-ssrf-spring-format
        new URL(url).openConnection();
        // ruleid: tainted-ssrf-spring-format
        new URL(url).openStream();

        URL url = new URL(url);
        // ruleid: tainted-ssrf-spring-format
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String body = reader.lines().collect(Collectors.joining());
        return body;
    }

    @GetMapping("/api/foos")
    @ResponseBody
    protected void test2(@RequestParam String uri, @RequestParam String test) {
        HttpClient client = new HttpClient();
        String completeUri = String.format("http://%s", uri);
		URI uri = new URI(completeUri);

		HttpRequest r = HttpRequest.newBuilder(uri).build();
		// ruleid: tainted-ssrf-spring-format
		client.send(r, null);
    }

    @GetMapping("/api/foos")
    @ResponseBody
    protected void test3(@RequestParam String host, @RequestParam String test)  {
        ObjectMapper mapper = new ObjectMapper();

        String url = String.format("https://%s/path", host);
        try (HttpClient client=HttpClients.createDefault()) {
            // ruleid: tainted-ssrf-spring-format
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
        // ok: tainted-ssrf-spring-format
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
        String url = String.format("https://%s/path", host);
        Request request = new Request.Builder()
        // ruleid: tainted-ssrf-spring-format
           .url(url)
           .build(); // defaults to GET

        Response response = client.newCall(request).execute();

        APOD apod = mapper.readValue(response.body().byteStream(), APOD.class);

        System.out.println(apod.title);
    }

    @GetMapping("/tweets-blocking")
    public List<Tweet> test5(@RequestParam String uri) {
        log.info("Starting BLOCKING Controller!");
        final String fullUri = String.format("https://%s/path", uri);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Tweet>> response = restTemplate.exchange(
        // ruleid: tainted-ssrf-spring-format
        fullUri, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Tweet>>(){});

        List<Tweet> result = response.getBody();
        result.forEach(tweet -> log.info(tweet.toString()));
        log.info("Exiting BLOCKING Controller!");
        return result;
    }

    @GetMapping(value = "/tweets-non-blocking",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> test6(@RequestParam String uri) {
        String fullUri = String.format("https://%s", uri);
        log.info("Starting NON-BLOCKING Controller!");
        Flux<Tweet> tweetFlux = WebClient.create()
        .get()
        // ruleid: tainted-ssrf-spring-format
        .uri(fullUri)
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
            // ok: tainted-ssrf-spring-format
            .uri("https://www.google.com")
            .retrieve();
    }

    @GetMapping(value = "/tweets-non-blocking",
    produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> test6b(@RequestParam String test) {
        String url = String.format("http://%s", test);

        WebClient.ResponseSpec responseSpec = WebClient.builder()
        // ruleid: tainted-ssrf-spring-format
        .baseUrl(url)
        .defaultCookie("cookieKey", "cookieValue")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
        .build()
        .get()
        .retrieve();
    }

    @GetMapping("hello/{message}")
    public String hello(@PathVariable String message) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider");
        String path = String.format("http://%s:%s/provide/%s", serviceInstance.getHost(), serviceInstance.getPort(), message);
        // ok: tainted-ssrf-spring-format
        String result = restTemplate.getForObject(path, String.class);
        return String.format("%s from %s %s", result, serviceInstance.getHost(), serviceInstance.getPort());
    }

	@GetMapping("/echo-rest/{str}")
	public String rest(@PathVariable String str) {
        String SERVICE_PROVIDER_ADDRESS = "http://service-provider";
        // ok: tainted-ssrf-spring-format
		return urlCleanedRestTemplate
				.getForObject(String.format("%s/echo/%s", SERVICE_PROVIDER_ADDRESS, str),
						String.class);
    }

    @GetMapping("/test")
    public static List<String> getLinks(@RequestParam String url) throws IOException {
        List<String> result = new ArrayList<String>();
        String path = String.format("https://%s", url);
        // ruleid: tainted-ssrf-spring-format
        Document doc = Jsoup.connect(path).get();
        Elements links = doc.select("a");
        for (Element link : links) {
          result.add(link.absUrl("href"));
        }
        return result;
    }

    private void testing(@RequestParam String host, String link) {
        try {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        new RestTemplate()
            .exchange(
                // ruleid: tainted-ssrf-spring-format
                String.format("http://%s/testing/%s", host, link),
                HttpMethod.GET,
                httpEntity,
                Void.class);
        } catch (Exception e) {
        // don't care
        }
    }
}
