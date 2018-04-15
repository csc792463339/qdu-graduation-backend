package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.entity.User;

import java.util.List;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    public String getAllStudent() {
        logger.info("获取所有学生");
        List<User> studentList = userDao.selectAllStudent();
        return JSON.toJSONString(studentList);
    }
}
