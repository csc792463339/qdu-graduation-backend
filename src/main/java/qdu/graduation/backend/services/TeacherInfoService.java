package qdu.graduation.backend.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.ClassesDao;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.entity.Classes;
import qdu.graduation.backend.entity.User;
import qdu.graduation.backend.support.StatusCode;

import java.util.List;

/**
 * Created by Jay on 2018/4/5.
 */
@Service
public class TeacherInfoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private UserDao userDao;

    public String getClassesInfo() {
        try {
//            Classes classes = classesDao.selectClassesByUserId(8);

//            if (classes == null) {
//                return "{\"code\":\"" + "1" + "\",\"msg\":\"" + "没有管理的班级" + "\"}";
//            }

            JSONObject res = JSON.parseObject("{\"code\":\"" + "0" + "\",\"msg\":\"" + "有管理的班级" + "\"}");
//            res.put("claId", classes.getClassId());
//            res.put("claName", classes.getClassName());
//            res.put("claStudentaccount", classes.getClassStudentaccount());
            return res.toJSONString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "{\"code\":\"" + "1" + "\",\"msg\":\"" + "没有管理的班级" + "\"}";
        }
    }

    //获取所有Theacher的信息和他的班级数量，将password设置成班级数量
    public String getAllTecharAndClassInfo() {
        List<User> teacherList = userDao.selectAllTeacher();
        for (User user : teacherList) {
            List<Classes> classesList = classesDao.selectAllClassesByTeacherId(user.getUserId());
            user.setUserPassword(String.valueOf(classesList.size()));
        }
        return JSON.toJSONString(teacherList).replaceAll("userPassword", "classCount");
    }

    public String changeUser(User user) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.success.toString());
            userDao.updateByPrimaryKeySelective(user);
            return res.toJSONString();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSON.parseObject(StatusCode.error.toString()).toJSONString();
        }
    }
}
