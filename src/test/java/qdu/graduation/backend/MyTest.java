package qdu.graduation.backend;

import redis.clients.jedis.Jedis;

public class MyTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("47.94.205.153", 6379);
        jedis.set("hello","world");
        System.out.println(jedis.get("hello"));
    }
}
