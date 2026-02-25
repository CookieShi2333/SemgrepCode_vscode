import redis.clients.jedis.Jedis;

@Service
public class JedisService implements IJedisService {
    @Test
    public void startWithUrl() {
        // ruleid: jedis-hardcoded-uri
        Jedis jedis = new Jedis("redis://default:yPqm07QgkiXFbZ9gxR9ejjpmuhO3j9sG@redis-18384.c16.us-east-1-2.ec2.cloud.redislabs.com:18384");

        // ok: jedis-hardcoded-uri
        try (Jedis j = new Jedis("localhost", 6379)) {
            j.set("foo", "bar");
        }

        // ruleid: jedis-hardcoded-uri
        try (Jedis j2 = new Jedis("redis://foobared:fizzbuzz@localhost:6379/2")) {
            j2.set("foo", "bar");
        }

        // ruleid: jedis-hardcoded-uri
        Jedis jedis = new Jedis("redis://aasdf:foobared@localhost:6380/2");

        String host = "fizzbuzz"
        String port = "3456"
        // ruleid: jedis-hardcoded-uri
        Jedis jedis = new Jedis("redis://:foobared@" + host + ":" + port + "/3");
    }
}
