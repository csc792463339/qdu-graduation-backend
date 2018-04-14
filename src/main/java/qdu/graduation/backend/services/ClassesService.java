package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.BoardDao;
import qdu.graduation.backend.dao.ClassesDao;
import qdu.graduation.backend.entity.Board;
import qdu.graduation.backend.entity.Classes;
import qdu.graduation.backend.support.StatusCode;

/**
 * Created by Jay on 2018/4/11.
 */
@Service
public class ClassesService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private BoardDao boardDao;

    public String getClassesById(Integer teacherId) {
        JSONObject res = JSON.parseObject("{\"code\":\"" + "0" + "\",\"msg\":\"" + "成功获取班级" + "\"}");
        res.put("classes", classesDao.selectAllClassesById(teacherId));
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

    public String getClassBoard(Integer classId) {
        logger.info("获取班级留言板:" + classId);
        return JSON.toJSONString(boardDao.selectByClassId(classId));
    }

    public String insertBoardMessage(Board board) {
        logger.info("班级留言板插入一条数据");
        int i = boardDao.insert(board);
        JSONObject res;
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
}
