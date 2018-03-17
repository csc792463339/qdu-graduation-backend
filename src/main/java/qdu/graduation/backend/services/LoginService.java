package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.UserDao;
import qdu.graduation.backend.dao.cache.RedisClient;
import qdu.graduation.backend.entity.User;
import qdu.graduation.backend.support.StatusCode;

@Service
public class LoginService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserDao userDao;

    public String login(String phone, String password) {
        try {
            User user = userDao.selectByPhone(phone);
            if (user == null) {
                logger.info(phone + ":" + StatusCode.userNotExist.toString());
                return StatusCode.userNotExist.toString();
            }
            if (!user.getUserPassword().equals(password)) {
                logger.info(phone + ":" + StatusCode.passWrong.toString());
                return StatusCode.passWrong.toString();
            }

            logger.info(user.getUserType() + ":" + phone + ":登录成功:" + StatusCode.success.toString());
            redisClient.hset(phone, "name", user.getUserName());
            redisClient.hset(phone, "type", user.getUserType());
            redisClient.expire(phone, 86400);

            JSONObject res = JSON.parseObject(StatusCode.success.toString());
            res.put("type", user.getUserType());
            res.put("name", user.getUserName());
            res.put("phone", user.getUserPhone());
            return res.toJSONString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return StatusCode.failed.toString();
        }
    }
}