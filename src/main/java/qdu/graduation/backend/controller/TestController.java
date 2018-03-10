package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.TestService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TestService testService;

    @RequestMapping(value = "/hello")
    @ResponseBody
    public Object getResult() {
        logger.info("TestController---收到请求");
        return testService.getResult();
    }
}
