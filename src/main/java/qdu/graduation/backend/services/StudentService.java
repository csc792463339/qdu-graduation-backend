package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.HomeworkDao;
import qdu.graduation.backend.dao.StudentClassDao;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.Homework;
import qdu.graduation.backend.entity.StudentClass;
import qdu.graduation.backend.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentClassDao studentClassDao;

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private RedisClient redisClient;

    public String getAllStudent() {
        logger.info("获取所有学生");
        List<User> studentList = userDao.selectAllStudent();
        for (User user : studentList) {
            List<StudentClass> studentClasses = studentClassDao.getAllClassByStudentID(user.getUserId());
            user.setUserType(String.valueOf(studentClasses.size()));
        }
        return JSON.toJSONString(studentList).replaceAll("userType", "classCount");
    }

    //获取未完成的作业
    public String notDoneHomework(Integer studentId) {
        String key = studentId + ":homework";
        Map<String, String> workMap = redisClient.hgetall(key);
        List<Map<String, String>> resultList = new ArrayList<>();
        for (String homeworkId : workMap.keySet()) {
            Map<String, String> resMap = new HashMap<>();
            Homework homework = homeworkDao.selectByPrimaryKey(Integer.parseInt(homeworkId));
            User user = userDao.selectByPrimaryKey(homework.getTeacherId());
            resMap.put("homwworkId", homeworkId);
            resMap.put("homeworkName", homework.getHomeworkName());
            resMap.put("teacherName", user.getUserName());
            resMap.put("publishTime", String.valueOf(homework.getCreateTime().getTime()));
            resMap.put("deadLine", workMap.get(homeworkId));
            resultList.add(resMap);
        }
        return JSON.toJSONString(resultList);
    }
}
