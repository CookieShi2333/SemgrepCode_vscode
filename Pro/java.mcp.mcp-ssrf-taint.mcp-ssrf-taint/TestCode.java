import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.ai.mcp.annotation.Tool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Vulnerable MCP tool implementations - SSRF via arbitrary URLs

class McpServer {

    @Tool(description = "Fetch content from a URL")
    public String fetchUrl(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            // ruleid: mcp-ssrf-taint
            .url(url)
            .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public String fetchWithApacheClient(String toolName, Map<String, Object> arguments) throws Exception {
        String url = (String) arguments.get("url");
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        // ok: mcp-ssrf-taint
        return client.execute(request).toString();
    }


    public String postWithApacheClient(String toolName, Map<String, Object> arguments) throws Exception {
        String url = (String) arguments.get("url");
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request = new HttpPost(url);
        // ok: mcp-ssrf-taint
        return client.execute(request).toString();
    }


    @Tool(description = "Fetch with OkHttp POST")
    public String fetchWithPost(String url, String data) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            // ruleid: mcp-ssrf-taint
            .url(url)
            .post(okhttp3.RequestBody.create(data, null))
            .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    @Tool(description = "Fetch with custom headers")
    public String fetchWithHeaders(String url, String headerValue) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            // ruleid: mcp-ssrf-taint
            .url(url)
            .addHeader("X-Custom-Header", headerValue)
            .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public String fetchWithOkHttp(String toolName, Map<String, Object> arguments) throws Exception {
        String url = (String) arguments.get("url");
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
            // ok: mcp-ssrf-taint
            .url(url)
            .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public String fetchInternal(String toolName, Map<String, Object> arguments) throws Exception {
        String endpoint = (String) arguments.get("endpoint");
        URL url = new URL("http://internal-service/" + endpoint);

        return url.openStream().toString();
    }


    public String fetchMetadata(String toolName, Map<String, Object> arguments) {
        String path = (String) arguments.get("path");
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://169.254.169.254/latest/meta-data/" + path;

        return restTemplate.getForObject(url, String.class);
    }


    public String fetchLocalhost(String toolName, Map<String, Object> arguments) throws Exception {
        String port = (String) arguments.get("port");
        String path = (String) arguments.get("path");
        URL url = new URL("http://localhost:" + port + "/" + path);

        return url.openConnection().toString();
    }


    public String scanPort(String toolName, Map<String, Object> arguments) {
        try {
            String host = (String) arguments.get("host");
            String port = (String) arguments.get("port");
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://" + host + ":" + port;

            restTemplate.getForObject(url, String.class);
            return "open";
        } catch (Exception e) {
            return "closed";
        }
    }


    public String fetchWithUrlBuilder(String toolName, Map<String, Object> arguments) throws Exception {
        String targetUrl = (String) arguments.get("url");
        URL url = new URL(targetUrl);

        return url.openStream().toString();
    }

    // Safe MCP tool implementations

    public String fetchFromApi(String toolName, Map<String, Object> arguments) {
        String path = (String) arguments.get("path");
        String allowedDomain = "https://api.example.com";
        RestTemplate restTemplate = new RestTemplate();
        // ok: mcp-ssrf-taint
        return restTemplate.getForObject(allowedDomain + "/" + path, String.class);
    }

    public String fetchValidatedUrl(String toolName, Map<String, Object> arguments) {
        String url = (String) arguments.get("url");
        String[] allowedDomains = {
            "https://api.example.com",
            "https://trusted.example.com"
        };
        boolean isAllowed = false;
        for (String domain : allowedDomains) {
            if (url.equals(domain)) {
                isAllowed = true;
                break;
            }
        }
        if (!isAllowed) {
            throw new IllegalArgumentException("URL not in allowlist");
        }
        RestTemplate restTemplate = new RestTemplate();
        // ok: mcp-ssrf-taint
        return restTemplate.getForObject(url, String.class);
    }

    public String fetchWithPrefixCheck(String toolName, Map<String, Object> arguments) {
        String url = (String) arguments.get("url");
        if (!url.startsWith("https://api.example.com/")) {
            throw new IllegalArgumentException("Invalid URL prefix");
        }
        RestTemplate restTemplate = new RestTemplate();
        // ok: mcp-ssrf-taint
        return restTemplate.getForObject(url, String.class);
    }

    public String fetchWithUriParse(String toolName, Map<String, Object> arguments) throws Exception {
        String url = (String) arguments.get("url");
        URI uri = new URI(url);
        String[] allowedHosts = {"api.example.com", "trusted.example.com"};
        boolean isAllowed = false;
        for (String host : allowedHosts) {
            if (host.equals(uri.getHost())) {
                isAllowed = true;
                break;
            }
        }
        if (!isAllowed) {
            throw new IllegalArgumentException("Invalid hostname");
        }
        RestTemplate restTemplate = new RestTemplate();
        // ok: mcp-ssrf-taint
        return restTemplate.getForObject(url, String.class);
    }

    public String fetchHardcoded(String toolName, Map<String, Object> arguments) {
        RestTemplate restTemplate = new RestTemplate();
        // ok: mcp-ssrf-taint
        return restTemplate.getForObject("https://api.example.com/data", String.class);
    }

    // Add executeTool method to match source pattern
    public String executeTool(String toolName, Map<String, Object> arguments) throws Exception {
        switch (toolName) {
            case "fetch_url":
                return fetchUrl(toolName, arguments);
            case "fetch_metadata":
                return fetchMetadata(toolName, arguments);
            default:
                return "unknown tool";
        }
    }
}
