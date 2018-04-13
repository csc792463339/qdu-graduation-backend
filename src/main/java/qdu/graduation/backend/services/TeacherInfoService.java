package qdu.graduation.backend.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.ClassesDao;
import qdu.graduation.backend.entity.Classes;

/**
 * Created by Jay on 2018/4/5.
 */
@Service
public class TeacherInfoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClassesDao classesDao;

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
}
