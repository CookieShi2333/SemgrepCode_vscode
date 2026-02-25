import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;

public class Example {

	private static final String Secret = "Password";
	private static final String ENCODED_SECRET = Base64.getEncoder().encodeToString(Secret.getBytes());

	public void fetchUsingHttpURLConnection() throws IOException {
		URL url = new URL(FETCH_URL); 
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
        // ruleid: base64-encoded-hardcoded-secret
		conn.setRequestProperty("Authorization", "Basic " + ENCODED_SECRET); 
		conn.connect();
		System.out.println(conn.getResponseCode());
	}

	public void fetchUsingHttpComponents() throws IOException {
		HttpGet httpGet = new HttpGet(FETCH_URL); 
        // ruleid: base64-encoded-hardcoded-secret
		httpGet.setHeader("Authorization", "Basic " + ENCODED_SECRET); 
		HttpClients.createDefault().execute(httpGet);
	}
}
