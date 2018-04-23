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
import qdu.graduation.backend.dao.StudentClassDao;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.Classes;
import qdu.graduation.backend.entity.StudentClass;
import qdu.graduation.backend.entity.User;
import qdu.graduation.backend.support.StatusCode;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jay on 2018/4/22.
 */
@Service
public class StudentApprovalService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String APPROVAL = "student:approval:";

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ClassesDao classesDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentClassDao studentClassDao;

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

}
