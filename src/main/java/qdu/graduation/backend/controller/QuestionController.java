package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qdu.graduation.backend.entity.Question;
import qdu.graduation.backend.services.QuestionService;

import javax.annotation.Resource;
import java.util.Map;

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

    @RequestMapping(value = "/distribute", method = RequestMethod.POST)
    @ResponseBody
    public Object distribute(String questionIds) {
        logger.info("分发习题");
        logger.info("questionIds=" + questionIds);
        String ids = questionIds.substring(0, questionIds.length() - 1);
        String[] idArr = ids.split(",");
        logger.info(ids);
        for (String id : idArr) {
            logger.info(id);
        }
        return null;
    }
}
