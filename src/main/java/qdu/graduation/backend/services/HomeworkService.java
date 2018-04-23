package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.ClassesDao;
import qdu.graduation.backend.dao.HomeworkClassDao;
import qdu.graduation.backend.dao.HomeworkDao;
import qdu.graduation.backend.dao.StudentClassDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.Homework;
import qdu.graduation.backend.entity.HomeworkClass;
import qdu.graduation.backend.entity.StudentClass;
import qdu.graduation.backend.support.StatusCode;

import java.util.*;

/**
 * Created by Jay on 2018/4/18.
 */
@Service
public class HomeworkService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private StudentClassDao studentClassDao;

    @Autowired
    private HomeworkClassDao homeworkClassDao;

    public String insertHomework(Homework homework) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.questionInsertHomeWorkSuccess.toString());
            homeworkDao.insertSelective(homework);
            return res.toJSONString();
        } catch (Exception e) {
            return StatusCode.questionInsertHomeWorkFailed.toString();
        }
    }

    public String selectAllHomework(Integer teacherId) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.questionGetHomeWorkSuccess.toString());
            res.put("allHomework", homeworkDao.selectByTeacherId(teacherId));
            return res.toJSONString();
        } catch (Exception e) {
            return StatusCode.questionGetHomeWorkFailed.toString();
        }
    }

    public String selectByPrimaryKey(Integer homeworkId) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.questionGetHomeWorkSuccess.toString());
            res.put("homework", homeworkDao.selectByPrimaryKey(homeworkId));
            res.put("classes", classesDao.selectAllClasses());
            return res.toJSONString();
        } catch (Exception e) {
            return StatusCode.questionGetHomeWorkFailed.toString();
        }
    }

    public void distributeClass(String questionid, List<String> classlist) {
        try {
            for (String classone :
                    classlist) {
                HomeworkClass homeworkClass = new HomeworkClass();
                homeworkClass.setClassId(Integer.parseInt(classone));
                homeworkClass.setHomeworkId(Integer.parseInt(questionid));
                homeworkClass.setDeadline(new Date());
                homeworkClassDao.insert(homeworkClass);
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }

    public String distribute(String questionid, List<String> classlist) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.questionGetHomeWorkSuccess.toString());
            logger.info("classlist" + classlist.toString());
            logger.info("questionid" + questionid);
            List<StudentClass> studentClasses = new ArrayList<StudentClass>();
            String cids = "";
            for (String cid :
                    classlist) {
                studentClasses.addAll(studentClassDao.getAllStudentByClassID(Integer.parseInt(cid)));
                cids += cid;
            }
            List<String> studentsIds = new ArrayList<String>();
            Set set = new HashSet();
            for (StudentClass s :
                    studentClasses) {
                set.add(s.getUserId());
            }
            for (Iterator it = set.iterator(); it.hasNext(); ) {
                studentsIds.add(it.next().toString());
            }
            logger.info("获取班级里的所有同学的id" + studentsIds);
            logger.info("习题集" + questionid);
            String deadline = new Date().toString();
            for (int j = 0; j < studentsIds.size(); j++) {
                String key = questionid;
                String filed = studentsIds.get(j) + ":homework";
                String value = deadline;
                logger.info("key=" + key + ";filed=" + filed + ";value=" + value);
                redisClient.hset(filed, key, value);
            }
            return res.toJSONString();
        } catch (Exception e) {
            logger.info(e.toString());
            return StatusCode.questionGetHomeWorkFailed.toString();
        }
    }
}
