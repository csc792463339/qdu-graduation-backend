package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qdu.graduation.backend.services.TestService;

import javax.annotation.Resource;

@Controller
@CrossOrigin
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

    @RequestMapping(value = "/addAcount", method = RequestMethod.POST)
    @ResponseBody
    public Object addAcount(String account, String password) {
        logger.info("addAcount---" + account + "," + password);
        return testService.addUser(account, password);
    }

    @RequestMapping(value = "/allAcount", method = RequestMethod.GET)
    @ResponseBody
    public Object allAcount() {
        logger.info("Get All Account");
        return "";
    }

}
