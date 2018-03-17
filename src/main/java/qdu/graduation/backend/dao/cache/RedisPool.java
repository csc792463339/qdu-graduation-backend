package qdu.graduation.backend.dao.cache;

import org.springframework.stereotype.Component;
import qdu.graduation.backend.support.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class RedisPool {

    @Resource
    private RedisConfig redisConfig;

    private JedisPool jedisPool = null;

    @PostConstruct
    private void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisConfig.getMaxActive());
        config.setMaxIdle(redisConfig.getMaxIdle());
        config.setMaxWaitMillis(redisConfig.getMaxIdle());
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, redisConfig.getAddress(), redisConfig.getPort());
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public void returnResource(final Jedis jedis) {
        if (jedis != null) {
            //jedis.close();
            jedisPool.returnResourceObject(jedis);
        }
    }
}  