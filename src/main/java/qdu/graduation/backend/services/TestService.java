package qdu.graduation.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qdu.graduation.backend.dao.TestDao;

@Service
public class TestService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestDao testDao;

    public String getResult() {
        logger.info("TestService");
        return testDao.getResult();
    }

}

