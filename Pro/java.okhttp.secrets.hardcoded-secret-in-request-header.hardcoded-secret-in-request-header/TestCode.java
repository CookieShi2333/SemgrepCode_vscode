import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Credentials;

public class OkhttpSecretBasicAuth {
    private String private_secret = "dXNlcm5hbWU6cGFzc3dvcmQ="; // b64 encoded username:password basic auth
    private String jwt = "eyJhbGciOiasdf";
    private String jwt_fake = "eyJhbGciO!iasdf";
    private String a = "a";
    private String empty = "";

    public void run() {
        Request request = new Request.Builder()
            // ruleid: hardcoded-secret-in-request-header
            .header("Authorization", "Basic dXNlcm5hbWU6cGFzc3dvcmQ=")
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ruleid: hardcoded-secret-in-request-header
            .header("Authorization", "Basic aseyJhbGciOi")
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ruleid: hardcoded-secret-in-request-header
            .header("Authorization", "Basic " + "d293ZWU6d2Fob28=")
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ruleid: hardcoded-secret-in-request-header
            .header("Authorization", "Basic " + private_secret)
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        String secret = "d293ZWU6d2Fob28="; // b64 encoded username:password basic auth
        Request request = new Request.Builder()
            // ruleid: hardcoded-secret-in-request-header
            .header("Authorization", "Basic " + secret)
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ruleid: hardcoded-secret-in-request-header
            .header("Authorization", "Bearer " + jwt)
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ruleid: hardcoded-secret-in-request-header
            .header("Authorization", "Token " + "d293ZWU6d2Fob28=")
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ruleid: hardcoded-secret-in-request-header
            .header("Authorization", "Token d293ZWU6d2Fob28=")
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        String entropy_string = "d293ZWU6d2Fob28="
        Request request = new Request.Builder()
            // ruleid: hardcoded-secret-in-request-header
            .header("Authorization", "Token " + entropy_string)
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ok: hardcoded-secret-in-request-header
            .header("Authorization", "asdf908a7sd9ufjkjh23iyro789qwy8ouashluiash")
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ok: hardcoded-secret-in-request-header
            .header("Authorization", "asdf908a7sd9ufjkjh23iyro789qwy8ouashluiash")
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ok: hardcoded-secret-in-request-header
            .header("Authorization", "Basic eyJhbGciOi")
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ok: hardcoded-secret-in-request-header
            .header("Authorization", "Bearer " + jwt_fake)
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ok: hardcoded-secret-in-request-header
            .header("Authorization", "Bearer asdf" + jwt)
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ok: hardcoded-secret-in-request-header
            .header("Authorization", "Beare " + jwt)
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ok: hardcoded-secret-in-request-header
            .header("Authorization", "Basic " + a)
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ok: hardcoded-secret-in-request-header
            .header("Authorization", "Basic " + "")
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();

        Request request = new Request.Builder()
            // ok: hardcoded-secret-in-request-header
            .header("Authorization", "Basic " + empty)
            .url("https://httpbin.org/post")
            .post(requestBody)
            .build();
    }

    public void run2(Request req) {
        // ruleid: hardcoded-secret-in-request-header
        req.header("Authorization", "Basic " + private_secret)
          .url("https://httpbin.org/post")
          .post(requestBody)
          .build();
    }
}
