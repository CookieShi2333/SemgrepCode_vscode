import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisPoolConfig;

// To test this with wildcard imports
import redis.clients.*;
import redis.*;

public class RedisTools {
    /**
     * The constant LOGGER.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(JedisPooledFactory.class);

    private static volatile JedisPoolAbstract jedisPool = null;

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 6379;
    private static final int DATABASE = 0;

    private static final int SENTINEL_HOST_NUMBER = 3;

    private static final Configuration CONFIGURATION = ConfigurationFactory.getInstance();

    public static JedisPoolAbstract getJedisPoolInstance(JedisPoolAbstract... jedisPools) {
        JedisPoolAbstract tempJedisPool = null;
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        String masterName = CONFIGURATION.getConfig(ConfigurationKeys.STORE_REDIS_SENTINEL_MASTERNAME);

        Set<String> sentinels = new HashSet<>(SENTINEL_HOST_NUMBER);
        int db = CONFIGURATION.getInt(ConfigurationKeys.STORE_REDIS_DATABASE, DATABASE);

        String password = CONFIGURATION.getConfig(ConfigurationKeys.STORE_REDIS_PASSWORD);
        // ok: jedissentinelpool-constructor-hardcoded-secret
        tempJedisPool = new JedisSentinelPool(masterName, sentinels, poolConfig, 60000, password, db);

        String password2 = "uh oh!";
        // ruleid: jedissentinelpool-constructor-hardcoded-secret
        tempJedisPool = new JedisSentinelPool(masterName, sentinels, poolConfig, 60000, password2, db);

        // To test this with wildcard imports

        // ruleid: jedissentinelpool-constructor-hardcoded-secret
        tempJedisPool = new jedis.JedisSentinelPool(masterName, sentinels, poolConfig, 60000, password2, db);
        // ruleid: jedissentinelpool-constructor-hardcoded-secret
        tempJedisPool = new clients.jedis.JedisSentinelPool(masterName, sentinels, poolConfig, 60000, password2, db);
    }
}