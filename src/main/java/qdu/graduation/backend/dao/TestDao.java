package qdu.graduation.backend.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import qdu.graduation.backend.dao.cache.RedisClient;

@Repository
public class TestDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisClient redisClient;

    public String getResult() {
        logger.info("TestDao");
        String res = redisClient.get("hello");
        if (StringUtils.isEmpty(res)) {
            res = "No result";
        }
        return res;
    }
}
