import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.DefaultJedisClientConfig;

public class JedisTest {
    void run() {
        // ruleid: jedis-jedisclientconfig-hardcoded-password
        new DefaultJedisClientConfig(connectionTimeoutMillis, socketTimeoutMillis,
            blockingSocketTimeoutMillis, user, "asdf", database, clientName, ssl, sslSocketFactory,
            sslParameters, hostnameVerifier, hostAndPortMapper);
        // ruleid: jedis-jedisclientconfig-hardcoded-password
        DefaultJedisClientConfig.create(connectionTimeoutMillis, socketTimeoutMillis,
            blockingSocketTimeoutMillis, user, "asdf", database, clientName, ssl, sslSocketFactory,
            sslParameters, hostnameVerifier, hostAndPortMapper);
        // ok: jedis-jedisclientconfig-hardcoded-password
        new DefaultJedisClientConfig(connectionTimeoutMillis, socketTimeoutMillis,
            blockingSocketTimeoutMillis, user, "", database, clientName, ssl, sslSocketFactory,
            sslParameters, hostnameVerifier, hostAndPortMapper);
        // ok: jedis-jedisclientconfig-hardcoded-password
        new DefaultJedisClientConfig(connectionTimeoutMillis, socketTimeoutMillis,
            blockingSocketTimeoutMillis, user, config.getAuthPassword(), database, clientName, ssl, sslSocketFactory,
            sslParameters, hostnameVerifier, hostAndPortMapper);
        // ok: jedis-jedisclientconfig-hardcoded-password
        new DefaultJedisClientConfig(connectionTimeoutMillis, socketTimeoutMillis,
            blockingSocketTimeoutMillis, user, config.getAuthPassword("john"), database, clientName, ssl, sslSocketFactory,
            sslParameters, hostnameVerifier, hostAndPortMapper);
        // ok: jedis-jedisclientconfig-hardcoded-password
        new DefaultJedisClientConfig(connectionTimeoutMillis, socketTimeoutMillis,
            blockingSocketTimeoutMillis, user, config.getAuthPassword["john"], database, clientName, ssl, sslSocketFactory,
            sslParameters, hostnameVerifier, hostAndPortMapper);

        // ruleid: jedis-jedisclientconfig-hardcoded-password
        JedisClientConfig cc = DefaultJedisClientConfig.builder()
            .password("asdf")
            .ssl(useSsl)
            .build();

        // Connect to the Azure Cache for Redis over the TLS/SSL port using the key.
        Jedis jedis = new Jedis(cacheHostname, 6380, cc);

        // ruleid: jedis-jedisclientconfig-hardcoded-password
        cc.updatePassword("hello");

        // ok: jedis-jedisclientconfig-hardcoded-password
        cc.updatePassword("");

        DefaultJedisClientConfig.Builder builder = DefaultJedisClientConfig.builder();
        // ruleid: jedis-jedisclientconfig-hardcoded-password
        builder.password("asdf");
    }
}
