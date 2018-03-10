package qdu.graduation.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qdu.graduation.backend.dao.cache.RedisClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Autowired
    RedisClient client;

    @Test
    public void contextLoads() {
        client.set("aaa", "bbb");
        System.out.println(client.get("hello"));
    }

}
