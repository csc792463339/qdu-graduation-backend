package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.BoardDao;
import qdu.graduation.backend.dao.ClassesDao;
import qdu.graduation.backend.dao.StudentClassDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.Board;
import qdu.graduation.backend.entity.Classes;
import qdu.graduation.backend.entity.StudentClass;
import qdu.graduation.backend.support.StatusCode;

import java.util.Date;
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

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private RedisClient redisClient;


    public String getClassesById(Integer teacherId) {
        JSONObject res = JSON.parseObject(StatusCode.success.toString());
        res.put("classes", classesDao.selectAllClassesByTeacherId(teacherId));
        return res.toString();
    }

    public String insertClassesByTeacherId(Classes classes) {
        logger.info("插入班级:" + classes.toString());
        JSONObject res;
        try {
            classesDao.insertClassesByTeacherId(classes);
            res = JSON.parseObject(StatusCode.success.toString());
        } catch (Exception e) {
            logger.info(e.getMessage());
            res = JSON.parseObject(StatusCode.failed.toString());
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
        return JSON.toJSONString(classesList).replaceAll("teacherId", "studentCount");
    }

    public String getClassBoard(Integer classId) {
        logger.info("获取班级留言板:" + classId);
        return JSON.toJSONString(boardDao.selectByClassId(classId));
    }

    public String insertBoardMessage(String classId, String content, String userName) {
        logger.info("班级留言板插入一条数据");
        Board board = new Board();
        board.setClassId(Integer.parseInt(classId));
        board.setContent(content);
        board.setUserName(userName);
        board.setLikeCount(0);
        board.setCreateDate(new Date());
        int i = boardDao.insert(board);
        if (i == 1) {
            return StatusCode.success.toString();
        } else {
            return StatusCode.failed.toString();
        }
    }

    public String likeBoardMessage(Integer messageId) {
        logger.info("班级留言板点赞");
        Board board = boardDao.selectByPrimaryKey(messageId);
        board.setLikeCount(board.getLikeCount() + 1);
        int i = boardDao.updateByPrimaryKey(board);
        JSONObject res;
        if (i == 1) {
            return StatusCode.success.toString();
        } else {
            return StatusCode.failed.toString();
        }
    }

    public String findClass(Integer classId) {
        Classes classes = classesDao.selectByPrimaryKey(classId);
        return JSON.toJSONString(classes);
    }

    public String joinClass(Integer classId, Integer studentId, String studentName) {
        Classes classes = classesDao.selectByPrimaryKey(classId);
        redisClient.hset("student:approval:" + classId, String.valueOf(studentId), studentName);
        return StatusCode.success.toString();
    }
}
