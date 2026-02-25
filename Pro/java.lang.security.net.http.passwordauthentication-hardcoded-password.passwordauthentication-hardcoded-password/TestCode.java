import java.net.http.HttpRequest;
import java.net.PasswordAuthentication;

public class UhOh {
    public void run(){
        String b64token = "d293ZWU6d2Fob28=";
        String basictoken = "Basic d293ZWU6d2Fob28="

        var authClient = HttpClient
            .newBuilder()
            .authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // ruleid: passwordauthentication-hardcoded-password
                    new PasswordAuthentication("postman", "password".toCharArray());

                    char[] asdf = "password".toCharArray()
                    // ruleid: passwordauthentication-hardcoded-password
                    new PasswordAuthentication("postman", asdf);

                    // ok: passwordauthentication-hardcoded-password
                    new PasswordAuthentication("postman", "password");
                }
            })
            .build();
    }
}