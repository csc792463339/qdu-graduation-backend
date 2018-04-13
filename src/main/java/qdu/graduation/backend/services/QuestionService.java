package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.QuestionDao;
import qdu.graduation.backend.entity.Question;

import java.util.Date;


/**
 * Created by Jay on 2018/4/9.
 */
@Service
public class QuestionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionDao questionDao;

    public String getAllQuestion() {
        try {
            JSONObject res = JSON.parseObject("{\"code\":\"" + "0" + "\",\"msg\":\"" + "获取题库" + "\"}");
            res.put("questions", questionDao.selectAllQuestion());
            return res.toJSONString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "{\"code\":\"" + "1" + "\",\"msg\":\"" + "题库为空" + "\"}";
        }
    }

    public String getQuestionById(Integer questionId) {
        try {
            JSONObject res = JSON.parseObject("{\"code\":\"" + "0" + "\",\"msg\":\"" + "获取题库" + "\"}");
            res.put("question", questionDao.selectByPrimaryKey(questionId));
            return res.toJSONString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "{\"code\":\"" + "1" + "\",\"msg\":\"" + "题库为空" + "\"}";
        }
    }

    public String insertSelective(Question record) {
        try {
            JSONObject res = JSON.parseObject("{\"code\":\"" + "0" + "\",\"msg\":\"" + "插入习题成功" + "\"}");
            record.setCreateTime(new Date());
            questionDao.insertSelective(record);
            return res.toJSONString();
        } catch (Exception e) {
            return "{\"code\":\"" + "1" + "\",\"msg\":\"" + "插入习题失败" + "\"}";
        }
    }

}


