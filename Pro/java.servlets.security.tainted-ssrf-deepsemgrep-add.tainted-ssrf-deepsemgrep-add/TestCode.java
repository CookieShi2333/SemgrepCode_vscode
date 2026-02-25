
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
import org.apache.http.HttpRequest;

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

        String url = "https://" + param;

        // ruleid: tainted-ssrf-deepsemgrep-add
        new URL(url).openConnection();
        // ruleid: tainted-ssrf-deepsemgrep-add
        new URL(url).openStream();

        URL url = new URL(url);
        // ruleid: tainted-ssrf-deepsemgrep-add
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String body = reader.lines().collect(Collectors.joining());
        return body;
    }

    protected void test1(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
        HttpClient client;
		URI uri = new URI(request.getParameter("uri"));
		// ruleid: tainted-ssrf-deepsemgrep-add
		HttpRequest r = HttpRequest.newBuilder(uri).build();
		client.send(r, null);
    }

    protected void test2(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();

        String url = "http://" + request.getParameter("host") + "/path";

        try (HttpClient client=HttpClients.createDefault()) {
            // ruleid: tainted-ssrf-deepsemgrep-add
            HttpGet request = new HttpGet(url);

            APOD response = client.execute(request, httpResponse ->
                mapper.readValue(httpResponse.getEntity().getContent(), APOD.class));

            System.out.println(response.title);
        }
    }

    protected void test3(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();
        String url = "https://" + request.getParameter("host");
        Request request = new Request.Builder()
            // ruleid: tainted-ssrf-deepsemgrep-add
           .url(url)
           .build(); // defaults to GET

        Response response = client.newCall(request).execute();

        APOD apod = mapper.readValue(response.body().byteStream(), APOD.class);

        System.out.println(apod.title);
    }

    protected void test4(HttpServletRequest request, HttpServletResponse response) {
        String input = request.getParameter("input");
        String url = "example.com"
        // ok: tainted-ssrf-deepsemgrep-add
        HttpGet request = new HttpGet("https://www.example.com/" + input);

        String apiURL = "https://openapi.naver.com/v1/util/shorturl?url=" + urlText;
        // ok: tainted-ssrf-deepsemgrep-add
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        // ok: tainted-ssrf-deepsemgrep-add
        URL url = new URL(String.format("https://%s/%s", url, input)).openConnection();

        String path = "path"
        // ok: tainted-ssrf-deepsemgrep-add
        URL url = new URL(String.format("https://%s/%s/%s", url, path, input)).openConnection();
    }

    void test5(HttpServletRequest request, HttpServletResponse response) {
        String input = request.getParameter("input");
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = "https://" + input;
        Request request = new Request.Builder()
            .header(RuneLiteAPI.RUNELITE_AUTH, uuid.toString())
            .get()
            // ruleid: tainted-ssrf-deepsemgrep-add
            .url(url)
            .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("test")
        }
    }

    String test6(HttpServletRequest request, HttpServletResponse response) {
        String input = request.getParameter("input");
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = "https://" + input;
        try {
            // ruleid: tainted-ssrf-deepsemgrep-add
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

    @PostMapping("/test7")
    @ResponseBody
    public AttackResult test7(
        @RequestParam String email, HttpServletRequest request) {
      String host = request.getHeader("host");
      String url = "http://" + host;
      try {
          HttpHeaders httpHeaders = new HttpHeaders();
          HttpEntity httpEntity = new HttpEntity(httpHeaders);
          new RestTemplate()
              .exchange(
                  // ruleid: tainted-ssrf-deepsemgrep-add
                  url,
                  HttpMethod.GET,
                  httpEntity,
                  Void.class);
        } catch (Exception e) {
          // don't care
        }
    }

    @PostMapping("/test8")
    @ResponseBody
    public AttackResult test8(
        @RequestParam String email, HttpServletRequest request) {
        String host = request.getHeader("host");
        String url = "https://" + host;

        WebClient.ResponseSpec responseSpec = WebClient.builder()
        // ruleid: tainted-ssrf-deepsemgrep-add
        .baseUrl(url)
        .defaultCookie("cookieKey", "cookieValue")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
        .build()
        .get()
        .retrieve();
    }

    @Override
    public void test9(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        // some code
        response.setContentType("text/html;charset=UTF-8");

        String param = "";
        if (request.getHeader("BenchmarkTest00006") != null) {
        param = request.getHeader("BenchmarkTest00006");
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike
        // req.getParameter().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        String url = "https://" + param;

        // ruleid: tainted-ssrf-deepsemgrep-add
        new URL(url).openConnection();
        // ruleid: tainted-ssrf-deepsemgrep-add
        new URL(url).openStream();
        // ruleid: tainted-ssrf-deepsemgrep-add
        new URL(url).getContent();
  }

  @Override
  public void test10(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // some code
    response.setContentType("text/html;charset=UTF-8");

    String param = "";
    if (request.getHeader("BenchmarkTest00006") != null) {
      param = request.getHeader("BenchmarkTest00006");
    }

    // URL Decode the header value since req.getHeader() doesn't. Unlike
    // req.getParameter().
    param = java.net.URLDecoder.decode(param, "UTF-8");

    String url = "https://" + param;
    URL myUrl = new URL(url);

    // ruleid: tainted-ssrf-deepsemgrep-add
    new URL(url).openConnection();
    // ruleid: tainted-ssrf-deepsemgrep-add
    myUrl.openConnection();
    // ruleid: tainted-ssrf-deepsemgrep-add
    new URL(url).openStream();
    // ruleid: tainted-ssrf-deepsemgrep-add
    myUrl.openStream();
    // ruleid: tainted-ssrf-deepsemgrep-add
    new URL(url).getContent();
    // ruleid: tainted-ssrf-deepsemgrep-add
    myUrl.getContent();

    String url2 = "https://localhost/";
    URL myUrl2 = new URL(url2);

    // ok: tainted-ssrf-deepsemgrep-add
    new URL(url2).openConnection();
    // ok: tainted-ssrf-deepsemgrep-add
    myUrl2.openConnection();
    // ok: tainted-ssrf-deepsemgrep-add
    new URL(url2).openStream();
    // ok: tainted-ssrf-deepsemgrep-add
    myUrl2.openStream();
    // ok: tainted-ssrf-deepsemgrep-add
    new URL(url2).getContent();
    // ok: tainted-ssrf-deepsemgrep-add
    myUrl2.getContent();

  }
}
