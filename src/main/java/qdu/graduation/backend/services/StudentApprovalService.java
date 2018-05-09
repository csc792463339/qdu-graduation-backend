package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.dao.ClassesDao;
import qdu.graduation.backend.dao.QuestionDao;
import qdu.graduation.backend.dao.StudentClassDao;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.Classes;
import qdu.graduation.backend.entity.Question;
import qdu.graduation.backend.entity.StudentClass;
import qdu.graduation.backend.entity.User;
import qdu.graduation.backend.support.StatusCode;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jay on 2018/4/22.
 */
@Service
public class StudentApprovalService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String APPROVAL = "student:approval:";
    private final String PERANSWER = ":perAnswer";//客观题队列
    private final String CORRECT = ":correct";//批改客观题

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private StudentClassDao studentClassDao;

    @Resource
    private StudentService studentService;

    public String getApprovalList(String teacherId) {
        try {
            List<Classes> classes = classesDao.selectAllClassesByTeacherId(Integer.parseInt(teacherId));
            String cid = "";
            List<String> sList = new ArrayList<String>();
            for (Classes c : classes
                    ) {
                cid = APPROVAL + c.getClassId().toString();
                Map<String, String> approvalList = redisClient.hgetall(cid);
                String s = "";
                for (String key : approvalList.keySet()) {
                    s += c.getClassId().toString() + "##" + key + "##" + approvalList.get(key);
                    sList.add(s);
                    s = "";
                }
            }
            logger.info("列表" + JSON.toJSONString(sList));
            JSONObject res = JSON.parseObject(StatusCode.approval.toString());
            if (sList.size() != 0) {
                res.put("list", sList);
                String[] arrString = sList.get(0).split("##");
                Integer studentId = Integer.parseInt(arrString[1]);
                User user = userDao.selectByPrimaryKey(studentId);
                res.put("studentId", studentId);
                res.put("userName", user.getUserName());
                res.put("userPhone", user.getUserPhone());
                res.put("classId", Integer.parseInt(arrString[0]));
                res.put("appClass", classesDao.selectByPrimaryKey(Integer.parseInt(arrString[0])).getClassName());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                res.put("appTime", df.format(new Date()));
            } else {
                res.put("appClass", "");
            }
            return res.toJSONString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusCode.failed.toString();
        }
    }

    public String studentPass(String classId, String studentId) {
        String queryName = APPROVAL + classId;
        redisClient.hdel(queryName, studentId);
        StudentClass studentClass = new StudentClass();
        studentClass.setClassId(Integer.parseInt(classId));
        studentClass.setUserId(Integer.parseInt(studentId));
        studentClassDao.insert(studentClass);
        return StatusCode.approval.toString();
    }

    public String studentReject(String classId, String studentId) {
        String queryName = APPROVAL + classId;
        redisClient.hdel(queryName, studentId);
        return StatusCode.reject.toString();
    }

    public String getPerAnswer(Integer homeworkId) {
        try {
            String homeworkAnswer = homeworkId + PERANSWER;
            Map<String, String> answerList = redisClient.hgetall(homeworkAnswer);
            List<String> perRecord = new ArrayList<String>();
            for (String h : answerList.keySet()) {
                logger.info("key:" + h + " value:" + answerList.get(h));
                perRecord.add(h + ":" + answerList.get(h));
            }
            JSONObject res = JSON.parseObject(StatusCode.success.toString());
            if (perRecord.size() != 0) {
                String now = perRecord.get(0);
                logger.info(now);
                String[] nowArr = now.split(":");
                String questionId = nowArr[0];
                String studentId = nowArr[1];
                String answer = nowArr[2];
                Question question = questionDao.selectByPrimaryKey(Integer.parseInt(questionId));
                User user = userDao.selectByPrimaryKey(Integer.parseInt(studentId));
                res.put("homeworkId", homeworkId);
                res.put("questionId", question.getQuestionId());
                res.put("userName", user.getUserName());
                res.put("questionContent", question.getQuestionContent());
                res.put("questionImg", question.getQuestionImg());
                res.put("questionScore", question.getQuestionScore());
                res.put("questionAnswer", answer);
                res.put("studentId", studentId);
                res.put("homeworkId", homeworkId);
                res.put("othersize", perRecord.size());
            } else {
                res.put("homeworkId", "");
            }
            return res.toJSONString();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return StatusCode.error.toString();
        }
    }

    public String insertAnswerScore(String studentId, String homeworkId, String questionId, String score) {
        try {
            String key = studentId + ":" + homeworkId + CORRECT;
            String field = questionId;
            String value = score;
            redisClient.hset(key, field, value);
            //删除这道题
            String homeworkAnswer = homeworkId + PERANSWER;
            redisClient.hdel(homeworkAnswer, questionId);
            studentService.calcHomeworkScore(Integer.parseInt(studentId), Integer.parseInt(homeworkId));
            return StatusCode.success.toString();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return StatusCode.error.toString();
        }
    }
}
