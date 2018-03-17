package qdu.graduation.backend.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("redisClient")
public class RedisClient {

    @Autowired
    RedisPool redisPool;

    /**
     * 通过key删除（字节）
     *
     * @param key
     */
    public void del(byte[] key) {
        Jedis jedis = redisPool.getJedis();
        jedis.del(key);
        redisPool.returnResource(jedis);
    }

    /**
     * 通过key删除
     *
     * @param key
     */
    public void del(String key) {
        Jedis jedis = redisPool.getJedis();
        jedis.del(key);
        redisPool.returnResource(jedis);
    }

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte[] key, byte[] value, int liveTime) {
        Jedis jedis = redisPool.getJedis();
        jedis.set(key, value);
        jedis.expire(key, liveTime);
        redisPool.returnResource(jedis);
    }

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key, String value, int liveTime) {
        Jedis jedis = redisPool.getJedis();
        jedis.set(key, value);
        jedis.expire(key, liveTime);
        redisPool.returnResource(jedis);
    }

    public void expire(String key, int liveTime) {
        Jedis jedis = redisPool.getJedis();
        jedis.expire(key, liveTime);
        redisPool.returnResource(jedis);
    }

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        Jedis jedis = redisPool.getJedis();
        jedis.set(key, value);
        redisPool.returnResource(jedis);
    }

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value) {
        Jedis jedis = redisPool.getJedis();
        jedis.set(key, value);
        redisPool.returnResource(jedis);
    }

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = redisPool.getJedis();
        String value = jedis.get(key);
        redisPool.returnResource(jedis);
        return value;
    }

    /**
     * 获取redis value (byte [] )(反序列化)
     *
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        Jedis jedis = redisPool.getJedis();
        byte[] value = jedis.get(key);
        redisPool.returnResource(jedis);
        return value;
    }

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = redisPool.getJedis();
        Set<String> value = jedis.keys(pattern);
        redisPool.returnResource(jedis);
        return value;
    }

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Jedis jedis = redisPool.getJedis();
        boolean value = jedis.exists(key);
        redisPool.returnResource(jedis);
        return value;
    }

    /*******************redis list操作************************/
    /**
     * 往list中添加元素
     *
     * @param key
     * @param value
     */
    public void lpush(String key, String value) {
        Jedis jedis = redisPool.getJedis();
        jedis.lpush(key, value);
        redisPool.returnResource(jedis);
    }

    public void rpush(String key, String value) {
        Jedis jedis = redisPool.getJedis();
        jedis.rpush(key, value);
        redisPool.returnResource(jedis);
    }

    /**
     * 数组长度
     *
     * @param key
     * @return
     */
    public Long llen(String key) {
        Jedis jedis = redisPool.getJedis();
        Long len = jedis.llen(key);
        redisPool.returnResource(jedis);
        return len;
    }

    /**
     * 获取下标为index的value
     *
     * @param key
     * @param index
     * @return
     */
    public String lindex(String key, Long index) {
        Jedis jedis = redisPool.getJedis();
        String str = jedis.lindex(key, index);
        redisPool.returnResource(jedis);
        return str;
    }

    public String lpop(String key) {
        Jedis jedis = redisPool.getJedis();
        String str = jedis.lpop(key);
        redisPool.returnResource(jedis);
        return str;
    }

    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = redisPool.getJedis();
        List<String> str = jedis.lrange(key, start, end);
        redisPool.returnResource(jedis);
        return str;
    }

    /*********************redis list操作结束**************************/


    public long hset(String key, String field, String value) {
        Jedis jedis = redisPool.getJedis();
        long res = jedis.hset(key, field, value);
        redisPool.returnResource(jedis);
        return res;
    }

    public String hget(String key, String field) {
        Jedis jedis = redisPool.getJedis();
        String value = jedis.hget(key, field);
        redisPool.returnResource(jedis);
        return value;
    }

    public Map<String, String> hgetall(String key) {
        Jedis jedis = redisPool.getJedis();
        Map<String, String> map = jedis.hgetAll(key);
        redisPool.returnResource(jedis);
        return map;
    }


    public long hdel(String key, String field) {
        Jedis jedis = redisPool.getJedis();
        long res = jedis.hdel(key, field);
        redisPool.returnResource(jedis);
        return res;
    }

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    public String flushDB() {
        Jedis jedis = redisPool.getJedis();
        String str = jedis.flushDB();
        redisPool.returnResource(jedis);
        return str;
    }

    /**
     * 查看redis里有多少数据
     */
    public long dbSize() {
        Jedis jedis = redisPool.getJedis();
        long len = jedis.dbSize();
        redisPool.returnResource(jedis);
        return len;
    }

    /**
     * 检查是否连接成功
     *
     * @return
     */
    public String ping() {
        Jedis jedis = redisPool.getJedis();
        String str = jedis.ping();
        redisPool.returnResource(jedis);
        return str;
    }
}