package org.owasp.benchmark.testcode;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

@WebServlet(value = "/cmdi-00/BenchmarkTest00006")
public class bad1 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // some code
        response.setContentType("text/html;charset=UTF-8");

        String param = "";
        if (request.getHeader("BenchmarkTest00006") != null) {
            param = request.getHeader("BenchmarkTest00006");
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        String url = String.format("https://%s", param);

        // ruleid: tainted-ssrf-deepsemgrep-format
        new URL(url).openConnection();
        // ruleid: tainted-ssrf-deepsemgrep-format
        new URL(url).openStream();

        URL url = new URL(url);
        // ruleid: tainted-ssrf-deepsemgrep-format
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String body = reader.lines().collect(Collectors.joining());
        return body;
    }

    protected void test1(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        String url = String.format("https://%s/path", request.getParameter("host"));

        try (HttpClient client=HttpClients.createDefault()) {
            // ruleid: tainted-ssrf-deepsemgrep-format
            HttpGet request = new HttpGet(url);

            APOD response = client.execute(request, httpResponse ->
                mapper.readValue(httpResponse.getEntity().getContent(), APOD.class));

            System.out.println(response.title);
        }
    }

    protected void test2(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();
        String url = String.format("https://%s/path", request.getParameter("host"));
        Request request = new Request.Builder()
            // ruleid: tainted-ssrf-deepsemgrep-format
           .url(url)
           .build(); // defaults to GET

        Response response = client.newCall(request).execute();

        APOD apod = mapper.readValue(response.body().byteStream(), APOD.class);

        System.out.println(apod.title);
    }

    protected void test3(HttpServletRequest request, HttpServletResponse response) {
        String input = request.getParameter("input");
        String url = String.format("https://www.example.com/%s", input);
        // ok: tainted-ssrf-deepsemgrep-format
        HttpGet request = new HttpGet(url);

        String apiURL = String.format("https://openapi.naver.com/v1/util/shorturl?url=%s", urlText);
        // ok: tainted-ssrf-deepsemgrep-format
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        // ok: tainted-ssrf-deepsemgrep-format
        URL url = new URL(String.format("https://%s/%s", apiURL, input)).openConnection();

        String path = "path"
        // ok: tainted-ssrf-deepsemgrep-format
        URL url = new URL(String.format("https://%s/%s/%s", apiURL, path, input)).openConnection();
    }

    void test4(HttpServletRequest request, HttpServletResponse response) {
        String input = request.getParameter("input");
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = String.format("https://%s", input);
        Request request = new Request.Builder()
            .header(RuneLiteAPI.RUNELITE_AUTH, uuid.toString())
            .get()
            // ruleid: tainted-ssrf-deepsemgrep-format
            .url(url)
            .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("test");
        }
    }

    String test5(HttpServletRequest request, HttpServletResponse response) {
        String input = request.getParameter("input");
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = String.format("https://%s", input);
        try {
            // ruleid: tainted-ssrf-deepsemgrep-format
            Connection.Response res = Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .timeout(10000)
                .cookies(cookies)
                .followRedirects(false)
                .execute();
            String imageURL = res.header("Location");
            LOGGER.info(imageURL);
            return imageURL;
            } catch (IOException e) {
              LOGGER.info("Got error message " + e.getMessage() + " trying to download " + url);
              return null;
            }
    }

    @PostMapping("/test6")
    @ResponseBody
    public AttackResult test6(
        @RequestParam String email, HttpServletRequest request) {
      String link = UUID.randomUUID().toString();
      String host = request.getHeader("host");
      try {
          HttpHeaders httpHeaders = new HttpHeaders();
          HttpEntity httpEntity = new HttpEntity(httpHeaders);
          new RestTemplate()
              .exchange(
                  // ruleid: tainted-ssrf-deepsemgrep-format
                  String.format("http://%s/testing/%s", host, link),
                  HttpMethod.GET,
                  httpEntity,
                  Void.class);
        } catch (Exception e) {
          // don't care
        }
    }

    @PostMapping("/test7")
    @ResponseBody
    public AttackResult test7(
        @RequestParam String email, HttpServletRequest request) {
        String host = request.getHeader("host");
        String url = String.format("http://%s", host);

        WebClient.ResponseSpec responseSpec = WebClient.builder()
        // ruleid: tainted-ssrf-deepsemgrep-format
        .baseUrl(url)
        .defaultCookie("cookieKey", "cookieValue")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
        .build()
        .get()
        .retrieve();
    }
}
