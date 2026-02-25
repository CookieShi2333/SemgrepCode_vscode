import java.net.http.HttpRequest;

public class UhOh {
    public void run(){
        String b64token = "d293ZWU6d2Fob28=";
        String basictoken = "Basic d293ZWU6d2Fob28="

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI("https://postman-echo.com/get"))
            // ruleid: httprequest-hardcoded-secret-in-request-header
            .headers("Authorization", "Basic " + b64token, "key2", "value2")
            // ruleid: httprequest-hardcoded-secret-in-request-header
            .header("Authorization", "Basic " + b64token)
            .GET()
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI("https://postman-echo.com/get"))
            // ok: httprequest-hardcoded-secret-in-request-header
            .headers("key1", "value1", "key2", "value2")
            // ruleid: httprequest-hardcoded-secret-in-request-header
            .header("Authorization", basictoken)
            .GET()
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI("https://postman-echo.com/get"))
            // ruleid: httprequest-hardcoded-secret-in-request-header
            .headers("Authorization", "Basic " + basictoken, "key2", "value2")
            // ok: httprequest-hardcoded-secret-in-request-header
            .headers("Authorization", "value1" + b64token, "key2", "value2")
            // ruleid: httprequest-hardcoded-secret-in-request-header
            .header("Authorization", "Basic d293ZWU6d2Fob28=")
            .GET()
            .build();

        HttpRequest request2 = HttpRequest.newBuilder()
            .uri(URI("https://postman-echo.com/get"))
            // ruleid: httprequest-hardcoded-secret-in-request-header
            .header("Authorization", "Bearer eyJhbGciOiasdf")
            .GET()
            .build();
    }

    public void run2(HttpRequest req) {
        req.newBuilder()
            .uri(URI("https://postman-echo.com/get"))
            // ruleid: httprequest-hardcoded-secret-in-request-header
            .header("Authorization", "Bearer eyJhbGciOiasdf")
            .GET()
            .build();
    }
}