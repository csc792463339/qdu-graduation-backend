package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.TeacherInfoService;

import javax.annotation.Resource;

/**
 * Created by Jay on 2018/4/6.
 */
@Controller
@CrossOrigin
@RequestMapping("/teacherinfo")
public class TeacherInfoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TeacherInfoService teacherInfoService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Object getClassInfo(Integer userid) {
        logger.info("用户-" + userid + "获取班级");
        return teacherInfoService.getClassesInfo();
    }

    @RequestMapping(value = "/getNews", method = RequestMethod.POST)
    @ResponseBody
    public Object getNews() {
        logger.info("获取前十条新闻");
        return teacherInfoService.getNews();
    }

    @RequestMapping(value = "/getNewsById", method = RequestMethod.POST)
    @ResponseBody
    public Object getNewsById(Integer newsId) {
        logger.info("获取新闻的详情" + newsId);
        return teacherInfoService.getNewsById(newsId);
    }

}
