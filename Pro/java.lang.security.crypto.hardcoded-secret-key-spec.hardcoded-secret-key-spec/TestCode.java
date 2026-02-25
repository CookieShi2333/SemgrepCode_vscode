import javax.crypto.*;
import javax.crypto.spec.*;

public class BadCiphers {

    private static void useCipher(Cipher cipher, String secret) throws Exception {
       String foo = "secret";
        // ruleid: hardcoded-secret-key-spec
       SecretKey key = new SecretKeySpec(foo.getBytes("UTF-8"), "AES");
        // ok: hardcoded-secret-key-spec
       SecretKey key = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");

    }
}
