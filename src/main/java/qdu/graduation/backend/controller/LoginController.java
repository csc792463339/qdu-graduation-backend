package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.LoginService;

import javax.annotation.Resource;

@Controller
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Object addAcount(String phone, String password) {
        logger.info("用户-" + phone + "开始登录");
        return loginService.login(phone, password);
    }
}
