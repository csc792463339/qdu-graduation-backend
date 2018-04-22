package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.HomeworkService;

import javax.annotation.Resource;

/**
 * Created by Jay on 2018/4/18.
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/homework")
public class HomeworkController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private HomeworkService homeworkService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Object list() {
        logger.info("获取题目集列表");
        return homeworkService.selectAllHomework();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Object detail(String homeworkId) {
        logger.info("获取" + homeworkId + "的详情");
        return homeworkService.selectByPrimaryKey(Integer.parseInt(homeworkId));
    }
}
