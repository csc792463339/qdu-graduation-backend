package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.ClassesDao;
import qdu.graduation.backend.dao.StudentClassDao;
import qdu.graduation.backend.entity.Classes;
import qdu.graduation.backend.entity.StudentClass;

import java.util.List;

/**
 * Created by Jay on 2018/4/11.
 */
@Service
public class ClassesService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private StudentClassDao studentClassDao;

    public String getClassesById(Integer teacherId) {
        JSONObject res = JSON.parseObject("{\"code\":\"" + "0" + "\",\"msg\":\"" + "成功获取班级" + "\"}");
        res.put("classes", classesDao.selectAllClassesByTeacherId(teacherId));
        return res.toString();
    }

    public String insertClassesByTeacherId(Classes classes) {
        logger.info("插入班级:" + classes.toString());
        JSONObject res;
        try {
            classesDao.insertClassesByTeacherId(classes);
            res = JSON.parseObject("{\"code\":\"" + "0" + "\",\"msg\":\"" + "成功插入班级" + "\"}");
        } catch (Exception e) {
            logger.info(e.getMessage());
            res = JSON.parseObject("{\"code\":\"" + "1" + "\",\"msg\":\"" + "插入班级失败" + "\"}");
        }
        return res.toString();
    }

    public String getAllClassAndStudentCount() {
        logger.info("获取所有班级和学生数量");
        List<Classes> classesList = classesDao.selectAllClasses();
        for (Classes classes : classesList) {
            List<StudentClass> studentList = studentClassDao.getAllStudentByClassID(classes.getClassId());
            classes.setTeacherId(studentList.size());
        }
        return JSON.toJSONString(classesList).replaceAll("teacherId","studentCount");
    }
}
