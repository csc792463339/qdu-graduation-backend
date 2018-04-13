package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.entity.Question;
import qdu.graduation.backend.services.QuestionService;

import javax.annotation.Resource;

/**
 * Created by Jay on 2018/4/9.
 */
@Controller
@CrossOrigin
@RequestMapping("/question")
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private QuestionService questionService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Object list() {
        logger.info("获取题目列表");
        return questionService.getAllQuestion();
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Object detail(Integer questionId) {
        logger.info("通过试题id找到有关内容");
        return questionService.getQuestionById(questionId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(Question question) {
        logger.info("添加题目");
        logger.info(question.toString());
        return questionService.insertSelective(question);
    }

}
