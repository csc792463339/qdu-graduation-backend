package qdu.graduation.backend.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.*;
import qdu.graduation.backend.entity.Classes;
import qdu.graduation.backend.entity.Homework;
import qdu.graduation.backend.entity.StudentHomework;
import qdu.graduation.backend.entity.User;
import qdu.graduation.backend.support.StatusCode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private StudentHomeworkDao studentHomeworkDao;

    @Autowired
    private StudentClassDao studentClassDao;

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

    public String getNews() {
        try {
            JSONObject res = JSON.parseObject(StatusCode.success.toString());
            res.put("news", newsDao.latestNew());
            return res.toJSONString();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSON.parseObject(StatusCode.error.toString()).toJSONString();
        }
    }

    public String getNewsById(Integer newsId) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.success.toString());
            res.put("news", newsDao.selectByPrimaryKey(newsId));
            return res.toJSONString();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSON.parseObject(StatusCode.error.toString()).toJSONString();
        }
    }

    public String getHomeworkList(Integer teacherId) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.success.toString());
            res.put("homeworkList", homeworkDao.selectByTeacherId(teacherId));
            return res.toJSONString();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSON.parseObject(StatusCode.error.toString()).toJSONString();
        }
    }

    public String getSubmitStatus(Integer teacherId) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.success.toString());
            logger.info("获取所有学生id");
            List<Homework> homeworkIds = homeworkDao.selectByTeacherId(teacherId);
            String homeworkIdStr = "";
            if (homeworkIds.size() != 0) {
                for (Homework h :
                        homeworkIds) {
                    homeworkIdStr += h.getHomeworkId() + ",";
                }
                homeworkIdStr = homeworkIdStr.substring(0, homeworkIdStr.length() - 1);
            }
            logger.info("习题集ids:" + homeworkIdStr);
            List<Integer> userIds = userDao.getStudentIdByTeacherId(teacherId);
            if (userIds.size() != 0 && homeworkIds.size() != 0) {
                logger.info("获取提交信息 ");
                String userIDs = "";
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (Integer id :
                        userIds) {
                    userIDs += id + ",";
                }
                userIDs = userIDs.substring(0, userIDs.length() - 1);
                logger.info("学生id" + userIDs);
                HashMap<String, String> paramMap = new HashMap<>();
                paramMap.put("userIDs", userIDs);
                paramMap.put("homeworkIdStr", homeworkIdStr);
                List<StudentHomework> homeworks = studentHomeworkDao.queryAllHomeworkByStudentIds(paramMap);
                List<Map<String, String>> submitStaMap = new ArrayList<Map<String, String>>();
                for (StudentHomework homework : homeworks
                        ) {
                    Map<String, String> submitStatu = new HashMap<String, String>();
                    submitStatu.put("homeName", homeworkDao.selectByPrimaryKey(homework.getHomeworkId()).getHomeworkName());
                    submitStatu.put("stuName", userDao.selectByPrimaryKey(homework.getStudentId()).getUserName());
                    submitStatu.put("submitRank", sf.format(homework.getCorrectTime()));
                    submitStatu.put("score", homework.getScore().toString());
                    submitStaMap.add(submitStatu);
                }
                res.put("submitStatus", submitStaMap);
            }
            return res.toJSONString();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSON.parseObject(StatusCode.error.toString()).toJSONString();
        }
    }

    public String getStudentRank(Integer teacherId) {
        try {
            JSONObject res = JSON.parseObject(StatusCode.success.toString());
            //获取homeworkid
            List<Homework> homeworkIds = homeworkDao.selectByTeacherId(teacherId);
            String homeworkIdStr = "";
            if (homeworkIds.size() != 0) {
                for (Homework h :
                        homeworkIds) {
                    homeworkIdStr += h.getHomeworkId() + ",";
                }
                homeworkIdStr = homeworkIdStr.substring(0, homeworkIdStr.length() - 1);
            }
            logger.info("习题集ids:" + homeworkIdStr);
            //获取homeworkid
            logger.info("获取所有学生id");
            List<Integer> userIds = userDao.getStudentIdByTeacherId(teacherId);
            if (userIds.size() != 0 && homeworkIds.size() != 0) {
                logger.info("获取提交信息");
                String userIDs = "";
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (Integer id :
                        userIds) {
                    userIDs += id + ",";
                }
                userIDs = userIDs.substring(0, userIDs.length() - 1);
                logger.info("学生id" + userIDs);
                HashMap<String, String> paramMap = new HashMap<>();
                paramMap.put("userIDs", userIDs);
                paramMap.put("homeworkIdStr", homeworkIdStr);
                List<HashMap<String, String>> userAvgs = studentHomeworkDao.getAvgScoreByStuId(paramMap);
                List<Map<String, String>> submitStaMap = new ArrayList<Map<String, String>>();
                for (int i = 0; i < userAvgs.size(); i++) {
                    Map<String, String> submitStatu = new HashMap<String, String>();
                    submitStatu.put("stuId", userAvgs.get(i).get("studentid"));
                    userAvgs.get(i).get("studentid");
                    logger.info("学生平均分信息：" + userAvgs.get(i).toString());
                    String studentid = String.valueOf(userAvgs.get(i).get("studentid"));
                    submitStatu.put("stuName", userDao.selectByPrimaryKey(Integer.parseInt(studentid)).getUserName());
                    int j = i + 1;
                    submitStatu.put("order", j + "");
                    submitStatu.put("avgscore", userAvgs.get(i).get("avgscore"));
                    submitStaMap.add(submitStatu);
                }
                res.put("submitStatus", submitStaMap);
            }
            return res.toJSONString();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return JSON.parseObject(StatusCode.error.toString()).toJSONString();
        }
    }

    public String getClassInfo(Integer teacherId) {
        List<Classes> classes = classesDao.selectAllClassesByTeacherId(teacherId);
        List<Map<String, String>> result = new ArrayList<>();
        for (Classes classes1 : classes) {
            Map<String, String> map = new HashMap<>();
            map.put("classId", classes1.getClassId().toString());
            map.put("className", classes1.getClassName());
            result.add(map);
        }
        return JSON.toJSONString(result);
    }
}
