package qdu.graduation.backend;

import com.alibaba.fastjson.JSON;
import net.bytebuddy.asm.Advice;
import org.hibernate.validator.constraints.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qdu.graduation.backend.dao.BoardDao;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.Board;
import qdu.graduation.backend.entity.User;

import java.sql.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Autowired
    RedisClient redisClient;

    @Autowired
    UserDao userDao;

    @Autowired
    BoardDao boardDao;

    @Test
    public void contextLoads() {
        Board board = boardDao.selectByPrimaryKey(1);
        System.out.println(board.getContent());


        List<Board> list = boardDao.selectByClassId(1);
        System.out.println(list);


//        client.set("aaa", "bbb");
//        System.out.println(client.get("hello"));
//        User user = new User();
//        user.setUserName("csc");
//        user.setUserPassword("123456");
//        user.setUserPhone("123");
//        user.setUserType("student");
//        user.setCreateTime(new java.util.Date());
//
//        userDao.insert(user);
//        String id=userDao.existPhone("123");
//        System.out.println(id);

//        User user=userDao.selectByPhone("15621009628","123456");

//        System.out.println(JSON.toJSONString(user));

    }

}

