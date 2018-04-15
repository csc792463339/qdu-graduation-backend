package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.StudentClassDao;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.entity.StudentClass;
import qdu.graduation.backend.entity.User;

import java.util.List;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentClassDao studentClassDao;

    public String getAllStudent() {
        logger.info("获取所有学生");
        List<User> studentList = userDao.selectAllStudent();
        for (User user : studentList) {
            List<StudentClass> studentClasses = studentClassDao.getAllClassByStudentID(user.getUserId());
            user.setUserType(String.valueOf(studentClasses.size()));
        }
        return JSON.toJSONString(studentList).replaceAll("userType", "classCount");
    }
}
