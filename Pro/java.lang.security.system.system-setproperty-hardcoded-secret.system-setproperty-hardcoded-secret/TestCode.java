public class Client {
	public void connect() {
        // ruleid: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        // ruleid: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

        // ok: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.trustStorePassword", config);
        // ok: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.keyStorePassword", config);
	}

}

// --- Test Case 2 ---
class Client() {
	fun connect() {
        // ruleid: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        // ruleid: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

        // ok: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.trustStorePassword", config);
        // ok: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.keyStorePassword", config);

        // ok: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.trustStorePassword", "");
        // ok: system-setproperty-hardcoded-secret
        System.setProperty("javax.net.ssl.keyStorePassword", "");
	}

}