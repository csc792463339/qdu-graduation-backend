package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.RegisterService;

import javax.annotation.Resource;

@Controller
@CrossOrigin
@RequestMapping("/register")
public class RegisterController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RegisterService registerService;

    @RequestMapping(value = "/sendsms", method = RequestMethod.POST)
    @ResponseBody
    public Object addAcount(String phone) {
        logger.info("用户-" + phone + "申请注册");
        return registerService.sendSms(phone);
    }

    @RequestMapping(value = "/checkcode", method = RequestMethod.POST)
    @ResponseBody
    public Object checkCode(String phone, String code) {
        logger.info("用户-:" + phone + "验证短信");
        return registerService.checkCode(phone, code);
    }

    @RequestMapping(value = "/add/student", method = RequestMethod.POST)
    @ResponseBody
    public Object addStudent(String phone, String name, String password) {
        logger.info("学生-:" + phone + ",姓名:" + name + " 开始注册");
        return registerService.addStudent(phone, name, password);
    }

    @RequestMapping(value = "/add/teacher", method = RequestMethod.POST)
    @ResponseBody
    public Object addTeacher(String phone, String name, String password) {
        logger.info("老师-:" + phone + ",姓名:" + name + " 开始注册");
        return registerService.addTeacher(phone, name, password);
    }
}
