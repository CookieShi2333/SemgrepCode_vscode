import redis.clients.jedis.Jedis;

public class JedisTest {
    void run() {
        Jedis jedis = new Jedis();

        String password = "hi";
        // ruleid: jedis-auth-hardcoded-secret
        jedis.auth("asdf");
        // ruleid: jedis-auth-hardcoded-secret
        jedis.auth(password);
        // ok: jedis-auth-hardcoded-secret
        jedis.auth(1);
        // ok: jedis-auth-hardcoded-secret
        jedis.auth("");
        // ok: jedis-auth-hardcoded-secret
        jedis.auth(config.getAuthPassword());
        // ok: jedis-auth-hardcoded-secret
        jedis.auth(config.getAuthPassword("john"));
        // ok: jedis-auth-hardcoded-secret
        jedis.auth(config.getAuthPassword["john"]);
    }

    private String password;
    private String authInput = (String) plugin.getConfig().get("authkey");
    public Jedis getObject() throws Exception {
		if(host==null){
			throw new RuntimeException("Redis host must not be null!");
		}

		Jedis jedis = new Jedis(host, port,timeout);
		if(password!=null&&!"".equals(password)){
            // ok: jedis-auth-hardcoded-secret
			jedis.auth(password);

            // ok: jedis-auth-hardcoded-secret
			jedis.auth(authInput);

            // ok: jedis-auth-hardcoded-secret
			jedis.auth((String) plugin.getConfig().get("authkey"));
		}
		return jedis;
	}
}
