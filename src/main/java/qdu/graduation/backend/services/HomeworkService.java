package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.ClassesDao;
import qdu.graduation.backend.dao.HomeworkDao;
import qdu.graduation.backend.entity.Homework;
import qdu.graduation.backend.support.StatusCode;

/**
 * Created by Jay on 2018/4/18.
 */
@Service
public class HomeworkService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private ClassesDao classesDao;

    public String insertHomework(Homework homework) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.questionInsertHomeWorkSuccess.toString());
            homeworkDao.insertSelective(homework);
            return res.toJSONString();
        } catch (Exception e) {
            return StatusCode.questionInsertHomeWorkFailed.toString();
        }
    }

    public String selectAllHomework() {
        try {
            JSONObject res = JSON.parseObject(StatusCode.questionGetHomeWorkSuccess.toString());
            //    res.put("allHomework", homeworkDao.selectAllHomework());
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
}
