public class Client {
	public void connect() {
          String password = "foo";
          Properties jndiProps = new Properties();
          // ruleid: properties-hardcoded-secret
          jndiProps.put(Context.SECURITY_CREDENTIALS, "password");
          // ruleid: properties-hardcoded-secret
          jndiProps.put(Context.SECURITY_CREDENTIALS, password);
          // ok: properties-hardcoded-secret
          jndiProps.put(Context.SECURITY_CREDENTIALS, config);
	}
}