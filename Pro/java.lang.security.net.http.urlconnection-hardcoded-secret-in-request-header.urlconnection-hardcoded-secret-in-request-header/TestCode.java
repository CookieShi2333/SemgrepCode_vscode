import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;

public class UhOh {
    void run() {
        String b64token = "d293ZWU6d2Fob28=";
        String basictoken = "Basic d293ZWU6d2Fob28="

        URLConnection urlConnection =  new URL(URL).openConnection();
        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();
        HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) new URL(URL).openConnection();

        // Headers
        // ruleid: urlconnection-hardcoded-secret-in-request-header
        urlConnection.addRequestProperty("Authorization", basictoken);

        // ruleid: urlconnection-hardcoded-secret-in-request-header
        httpUrlConnection.addRequestProperty("Authorization", "Basic " + b64token);

        // ok: urlconnection-hardcoded-secret-in-request-header
        httpsUrlConnection.addRequestProperty("Authorization", "application/json");

        urlConnection.getInputStream().close();
        httpUrlConnection.getInputStream().close();
        httpsUrlConnection.getInputStream().close();
    }
}