package qdu.graduation.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SendSmsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean sendCode(String phone, String code) {
        logger.info(phone + ":正在发送短信验证码");
        return true;
    }

}
