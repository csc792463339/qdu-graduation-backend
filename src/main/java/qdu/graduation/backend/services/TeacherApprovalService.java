package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.User;
import qdu.graduation.backend.support.StatusCode;

import java.util.Map;

@Service
public class TeacherApprovalService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String APPROVAL = "approval";

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SendSmsService sendSmsService;

    public String getApprovalList() {
        try {
            Map<String, String> approvalList = redisClient.hgetall(APPROVAL);
            return JSON.toJSONString(approvalList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusCode.failed.toString();
        }
    }

    public String accectApproval(String phone) {
        try {
            String info = redisClient.hget(APPROVAL, phone);
            User user = JSON.parseObject(info, new TypeReference<User>() {
            });
            userDao.insert(user);
            sendSmsService.notice(phone, "教师审核", user.getUserName(), "已通过！");
            redisClient.hdel(APPROVAL, phone);
            return StatusCode.success.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusCode.failed.toString();
        }
    }

    public String rejectApproval(String phone) {
        try {
            redisClient.hdel(APPROVAL, phone);
            sendSmsService.notice(phone, "教师审核", "", "未通过！");
            return StatusCode.success.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusCode.failed.toString();
        }
    }
}