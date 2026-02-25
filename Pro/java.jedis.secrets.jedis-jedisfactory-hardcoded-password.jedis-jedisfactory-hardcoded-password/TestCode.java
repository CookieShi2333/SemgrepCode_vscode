import redis.clients.jedis.JedisFactory;

@Service
public class JedisService implements IJedisService {
    @Test
    public void hardcoded() {
        JedisFactory jedisFactory = new JedisFactory();
		jedisFactory.setHostName(hostName);
		jedisFactory.setPort(port);
        // ruleid: jedis-jedisfactory-hardcoded-password
		jedisFactory.setPassword("asdf");
		jedisFactory.setDatabase(database);
    }

    public void notHardcoded(String password) {
        JedisFactory jedisFactory = new JedisFactory();
		jedisFactory.setHostName(hostName);
		jedisFactory.setPort(port);
        // ok: jedis-jedisfactory-hardcoded-password
		jedisFactory.setPassword(password);
		jedisFactory.setDatabase(database);
    }
}
