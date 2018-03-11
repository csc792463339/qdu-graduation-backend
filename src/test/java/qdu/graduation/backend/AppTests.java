package qdu.graduation.backend;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Autowired
    RedisClient client;

    @Autowired
    UserDao userDao;

    @Test
    public void contextLoads() {
//        client.set("aaa", "bbb");
//        System.out.println(client.get("hello"));
        User user=new User();
        user.setId(1);
        user.setUserName("反反复复");
        user.setPassword("password");
        user.setAge(18);
     //   userDao.insert(user);
        String res=JSON.toJSONString(userDao.selectAll());
        System.out.println(res);

    }

}
