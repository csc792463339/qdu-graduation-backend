package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.TestDao;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.entity.User;

@Service
public class TestService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestDao testDao;

    @Autowired
    private UserDao userDao;

    public String getResult() {
        logger.info("TestService");
        return testDao.getResult();
    }

    public String addUser(String account, String password) {
        User user = new User();
//        user.setId(666);
//        user.setAge(18);
//        user.setUserName(account);
//        user.setPassword(password);
        return userDao.insert(user) + "";
    }

//    public String allUser() {
//        return JSON.toJSONString(userDao.selectAll());
//    }

}

