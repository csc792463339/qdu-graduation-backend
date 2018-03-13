package qdu.graduation.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.StatusCode;

@Service
public class RegisterService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserDao userDao;

    public String sendSms(String phone) {

        //查mysql
        if (userDao.equals("")) {
            logger.info(phone + ":" + StatusCode.phoneHasRegister.toString());
            return StatusCode.phoneHasRegister.toString();
        }
        //查redis
        if (redisClient.exists(phone + ":code")) {
            logger.info(phone + ":" + StatusCode.hasSendSms.toString());
            return StatusCode.hasSendSms.toString();
        }

        logger.info(phone + ":" + StatusCode.sendSms.toString());
        return StatusCode.sendSms.toString();
    }

    public String checkCode(String phone, String code) {
        String smscode = redisClient.get(phone + ":code");
        if (StringUtils.isEmpty(smscode)) {
            logger.info(phone + ":" + StatusCode.smsCodeInvalid.toString());
            return StatusCode.smsCodeInvalid.toString();
        }
        if (!smscode.equals(code)) {
            logger.info(phone + ":" + StatusCode.codeWrong.toString());
            return StatusCode.codeWrong.toString();
        }
        logger.info(phone + ":" + StatusCode.success.toString());
        return StatusCode.success.toString();
    }


    public String addStudent(String phone, String name, String password, String gender) {

        logger.info(phone + ":" + StatusCode.success.toString());
        return StatusCode.success.toString();
    }


}

