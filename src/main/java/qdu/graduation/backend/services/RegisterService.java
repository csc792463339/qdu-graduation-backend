package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.User;
import qdu.graduation.backend.support.StatusCode;
import qdu.graduation.backend.utils.CodeUtil;

import java.util.Date;

@Service
public class RegisterService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SendSmsService sendSmsService;

    public String sendSms(String phone) {
        try {
            //查mysql
            if (!StringUtils.isEmpty(userDao.existPhone(phone))) {
                logger.info(phone + ":" + StatusCode.phoneHasRegister.toString());
                return StatusCode.phoneHasRegister.toString();
            }
            //查redis
            if (redisClient.exists(phone)) {
                logger.info(phone + ":" + StatusCode.hasSendSms.toString());
                return StatusCode.hasSendSms.toString();
            }
            String code = CodeUtil.randomCode();
            if (sendSmsService.sendCode(phone, code)) {
                redisClient.hset(phone, "code", code);
                redisClient.expire(phone, 300);
                logger.info(phone + ":" + StatusCode.sendSms.toString());
                return StatusCode.sendSms.toString();
            } else {
                logger.info(phone + ":" + StatusCode.sendSmsFailed.toString());
                return phone + ":" + StatusCode.sendSmsFailed.toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusCode.failed.toString();
        }
    }

    public String checkCode(String phone, String code) {
        try {
            String sendCode = redisClient.hget(phone, "code");
            if (StringUtils.isEmpty(sendCode)) {
                logger.info(phone + ":" + StatusCode.smsCodeInvalid.toString());
                return StatusCode.smsCodeInvalid.toString();
            }
            if (!sendCode.equals(code)) {
                logger.info(phone + ":" + StatusCode.codeWrong.toString());
                return StatusCode.codeWrong.toString();
            }
            redisClient.hset(phone, "code", "true");
            redisClient.expire(phone, 86400);
            logger.info(phone + ":" + StatusCode.success.toString());
            return StatusCode.success.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusCode.failed.toString();
        }
    }

    public String addStudent(String phone, String name, String password) {
        try {
            String codeFlag = redisClient.hget(phone, "code");
            if (StringUtils.isEmpty(codeFlag) || !codeFlag.equals("true")) {
                return StatusCode.failed.toString();
            }
            User user = new User();
            user.setUserName(name);
            user.setUserPassword(password);
            user.setUserPhone(phone);
            user.setUserType("student");
            user.setCreateTime(new Date());
            userDao.insert(user);
            logger.info(phone + ":" + StatusCode.success.toString());
            return StatusCode.success.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusCode.failed.toString();
        }
    }

    public String addTeacher(String phone, String name, String password) {
        try {
            String codeFlag = redisClient.hget(phone, "code");
            if (StringUtils.isEmpty(codeFlag) || !codeFlag.equals("true")) {
                return StatusCode.failed.toString();
            }
            User user = new User();
            user.setUserName(name);
            user.setUserPassword(password);
            user.setUserPhone(phone);
            user.setUserType("teacher");
            user.setCreateTime(new Date());
            String content = JSON.toJSONString(user);
            redisClient.hset("approval", phone, content);
            logger.info(phone + ":" + StatusCode.waitApproval.toString());
            return StatusCode.waitApproval.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusCode.failed.toString();
        }
    }
}