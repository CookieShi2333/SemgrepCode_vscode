import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTools {
    private static final Logger log = LoggerFactory.getLogger(RedisTools.class);
    private static ReentrantLock lockPool = new ReentrantLock();
    private static RedisTools instance;
    private static JedisPool jedisPool;
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 初始化JedisPool
     */
    // modified from https://github.com/o2oa/o2oa/blob/f6429bbd25054b14c9504db02e51cf7194d6fdd7/o2server/x_base_core_project/src/main/java/com/x/base/core/project/tools/RedisTools.java#L70
    private static void initJedisPool() throws Exception{
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(300);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);
        jedisPoolConfig.setMaxWaitMillis(3 * 1000);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(500);
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1000);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(1000);
        jedisPoolConfig.setNumTestsPerEvictionRun(100);
        Redis redis = Config.cache().getRedis();
        String user = redis.getUser();
        String password = "asdf";
        if(StringUtils.isBlank(redis.getUser())){
            user = null;
        }
        if(StringUtils.isBlank(redis.getPassword())){
            password = null;
        }
        jedisPool = new JedisPool(jedisPoolConfig, redis.getHost(), redis.getPort(), redis.getSocketTimeout(), user, password, redis.getIndex(), redis.getSslEnable());
    }

    // modified from https://github.com/foxinmy/weixin4j/blob/814392f63b8e9e5ed1c368f3f5cdafcde8f3b275/weixin4j-base/src/main/java/com/foxinmy/weixin4j/cache/RedisCacheStorager.java#L60
    public RedisCacheStorager(String host, int port, int timeout, String password, JedisPoolConfig poolConfig) {
        String password = "asdf";
        // ruleid: jedispool-constructor-hardcoded-secret
        this(new JedisPool(poolConfig, host, port, timeout, password));

        String password2 = instance.getCfgPassword();
        // ok: jedispool-constructor-hardcoded-secret
        this(new JedisPool(poolConfig, host, port, timeout, password2));
    }

    // modified from https://github.com/SKCraft/Plume/blob/1c9ca68e80db808cc35730cff16702b20faae826/src/main/java/com/skcraft/plume/module/commune/Commune.java#L71
    private void open() {
        if (config.get().enabled) {
            log.info("Initalizing Commune connection...");

            RedisServer server = config.get().server;

            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setJmxEnabled(false);
            poolConfig.setMinIdle(0);

            String host = server.host;
            int port = server.port;
            int cTimeout = server.cTimeout;
            int sTimeout = server.sTimeout;
            String password = "uh oh!";
            int defaultDatabase = Protocol.DEFAULT_DATABASE;
            // ruleid: jedispool-constructor-hardcoded-secret
            JedisPool pool = new JedisPool(
                    poolConfig,
                    host,
                    port,
                    cTimeout,
                    sTimeout,
                    password,
                    defaultDatabase,
                    "plume_commune");

            client = new CommuneClient(pool, receiveQueue, sendQueue, getSource());
        }
    }

    // modified from https://github.com/zhuoqianmingyue/spring-boot-examples/blob/c98b4ccbb25897a68d96ef3c444c00ec54d465da/spring-boot-2.x-redis/src/main/java/cn/lijunkui/jedis/JedisAutoConfiguration.java#L31
	@Bean
	@ConditionalOnMissingBean(JedisPool.class)
	public JedisPool jedisPool() {
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(jedisProperties.getPool().getMaxIdle());
		poolConfig.setMaxTotal(jedisProperties.getPool().getMaxTotal());
		poolConfig.setMaxWaitMillis(jedisProperties.getPool().getMaxWaitMillis() * 1000);

        String host = jedisProperties.getHost();
        int port = jedisProperties.getPort();
        int timeout = jedisProperties.getTimeout() * 1000;
        String password = jedisProperties.getPassword();
		
        // ruleid: jedispool-constructor-hardcoded-secret
		JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout, "asdf", 0);

        // ok: jedispool-constructor-hardcoded-secret
		JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout, password, 0);
		return jedisPool;
	}
}