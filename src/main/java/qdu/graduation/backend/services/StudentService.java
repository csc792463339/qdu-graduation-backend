package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.*;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.*;

import java.text.DecimalFormat;
import java.util.*;

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

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private StudentHomeworkDao studentHomeworkDao;

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
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (String homeworkId : workMap.keySet()) {
            Map<String, Object> resMap = new HashMap<>();
            Homework homework = homeworkDao.selectByPrimaryKey(Integer.parseInt(homeworkId));
            User user = userDao.selectByPrimaryKey(homework.getTeacherId());
            resMap.put("homwworkId", homeworkId);
            resMap.put("homeworkName", homework.getHomeworkName());
            resMap.put("teacherName", user.getUserName());
            resMap.put("publishTime", homework.getCreateTime().getTime());
            resMap.put("deadLine", workMap.get(homeworkId));
            resultList.add(resMap);
        }
        return JSON.toJSONString(resultList);
    }

    public String getQuestions(Integer homeworkId) {
        Homework homework = homeworkDao.selectByPrimaryKey(homeworkId);
        String[] question = homework.getQuestionsId().split(",");
        List<Question> questions = new ArrayList<>();
        for (String qid : question) {
            Question que = questionDao.selectByPrimaryKey(Integer.parseInt(qid));
            que.setCorrectOption("");
            questions.add(que);
        }
        return JSON.toJSONString(questions);
    }


    public String submitAnswer(Integer studentId, Integer homeworkId, String questionId, String type, String answer) {

        if (type.equals("sel")) {
            String key = studentId + ":" + homeworkId + ":sel";
            redisClient.hset(key, questionId, answer);
        } else {
            String key = studentId + ":" + homeworkId + ":per";
            redisClient.hset(key, questionId, answer);
        }

        return "";
    }

    public String getOneAnswer(Integer studentId, Integer homeworkId, String questionId, String type) {
        if (type.equals("sel")) {
            String key = studentId + ":" + homeworkId + ":sel";
            return redisClient.hget(key, questionId);
        } else {
            String key = studentId + ":" + homeworkId + ":per";
            return redisClient.hget(key, questionId);
        }
    }

    public String getDoneAnswer(Integer studentId, Integer homeworkId) {
        String key = studentId + ":" + homeworkId + ":sel";
        Map<String, String> answer = redisClient.hgetall(key);
        key = studentId + ":" + homeworkId + ":per";
        answer.putAll(redisClient.hgetall(key));
        return JSON.toJSONString(answer);
    }

    public String submitAllAnswer(Integer studentId, Integer homeworkId) {
        String key = studentId + ":" + homeworkId + ":per";
        Map<String, String> perAnswer = redisClient.hgetall(key);
        long rank = redisClient.incr(homeworkId + ":countSubmit");
        redisClient.hset(studentId + ":" + homeworkId + ":rank", "rank", rank + "");
        if (!perAnswer.isEmpty()) {
            String queueId = homeworkId + ":perAnswer";
            for (String questionId : perAnswer.keySet()) {
                logger.info("学生 studentId:{} 的作业 homewordId:{} 有主观题还未批改", studentId, homeworkId);
                redisClient.hset(queueId, questionId, studentId + "###" + perAnswer.get(questionId));
            }
        } else {
            calcHomeworkScore(studentId, homeworkId);
        }
        return rank + "";
    }

    //取客观题队列:  队列名：homeworkId:perAnswer   取出来 key：questionID value：(studentId###answer)
    //老师批改完 将分数插入redis。 key = studentId + ":" + homeworkId + ":correct"   field：questionID   value:score
    //插入完 调一下 StudentService的calcHomeworkScore(studentId,homeworkId) 去算分
    public String calcHomeworkScore(Integer studentId, Integer homeworkId) {
        String key = studentId + ":" + homeworkId + ":per";
        Map<String, String> perAnswer = redisClient.hgetall(key);
        int fullScore = 0;
        int getScore = 0;
        List<Map<String, String>> resList = new ArrayList<>();
        Map<String, String> resContent = null;
        if (!perAnswer.isEmpty()) {
            key = studentId + ":" + homeworkId + ":correct";
            Map<String, String> correctRes = redisClient.hgetall(key);
            if (perAnswer.size() != correctRes.size()) {
                logger.info("学生 studentId:{} 的作业 homewordId:{} 有主观题还未批改", studentId, homeworkId);
                return "";
            } else {
                for (String questionId : correctRes.keySet()) {
                    Question question = questionDao.selectByPrimaryKey(Integer.parseInt(questionId));
                    resContent = new HashMap<>();
                    resContent.put("questionId", questionId);
                    resContent.put("correctAnswer", "");
                    resContent.put("myAnswer", perAnswer.get(questionId));
                    resContent.put("questionScore", question.getQuestionScore() + "");
                    fullScore += question.getQuestionScore();
                    getScore += Integer.parseInt(correctRes.get(key));
                    resContent.put("myScore", correctRes.get(key));
                    resList.add(resContent);
                }
            }
        }
        String selKey = studentId + ":" + homeworkId + ":sel";
        Map<String, String> selAnswer = redisClient.hgetall(selKey);
        for (String questionId : selAnswer.keySet()) {
            String answer = selAnswer.get(questionId);
            Question question = questionDao.selectByPrimaryKey(Integer.parseInt(questionId));
            resContent = new HashMap<>();
            resContent.put("questionId", questionId);
            resContent.put("correctAnswer", question.getCorrectOption());
            resContent.put("myAnswer", answer);
            resContent.put("questionScore", question.getQuestionScore() + "");
            fullScore += question.getQuestionScore();
            if (answer.equals(question.getCorrectOption())) {
                getScore += question.getQuestionScore();
                resContent.put("myScore", question.getQuestionScore() + "");
            } else {
                resContent.put("myScore", "0");
            }
            resList.add(resContent);
        }
        Double score = ((double) getScore / (double) fullScore) * 100;
        DecimalFormat df = new DecimalFormat("######0");
        StudentHomework studentHomework = new StudentHomework();
        studentHomework.setHomeworkId(homeworkId);
        studentHomework.setStudentId(studentId);
        studentHomework.setStudentAnswer(JSON.toJSONString(resList));
        studentHomework.setScore(Integer.parseInt(df.format(score)));
        studentHomework.setCorrectTime(new Date());
        String rank = redisClient.hget(studentId + ":" + homeworkId + ":rank", "rank");
        studentHomework.setSubmitRank(Integer.parseInt(rank));
        studentHomeworkDao.insert(studentHomework);
        logger.info("StudentId:{},HomeworkId:{}，批改完成,得分:{}", studentId, homeworkId, score);
        return "";
    }
}
