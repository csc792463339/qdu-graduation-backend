package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qdu.graduation.backend.entity.Homework;
import qdu.graduation.backend.entity.Question;
import qdu.graduation.backend.services.HomeworkService;
import qdu.graduation.backend.services.QuestionService;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private HomeworkService homeworkService;

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
    public Object distribute(String questionIds, String questionListName) {
        logger.info("创建习题集" + questionListName);
        logger.info("questionIds=" + questionIds);
        String ids = questionIds.substring(0, questionIds.length() - 1);
        String[] idArr = ids.split(",");
        logger.info(ids);
        Homework homework = new Homework();
        homework.setCreateTime(new Date());
        homework.setFullScore(questionService.getSumScore(ids));
        homework.setQuestionsId(ids);
        return homeworkService.insertHomework(homework);
    }
}
