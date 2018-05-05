package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.StudentService;

import javax.annotation.Resource;

@Controller
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StudentService studentService;

    @RequestMapping(value = "/notdoneHomework", method = RequestMethod.POST)
    @ResponseBody
    public Object notdoneHomework(Integer studentId) {
        logger.info("学生-" + studentId + "查看未完成的作业");
        return studentService.notDoneHomework(studentId);
    }

    @RequestMapping(value = "/getQuestions", method = RequestMethod.POST)
    @ResponseBody
    public Object getQuestions(Integer homeworkId) {
        logger.info("查看作业题目-{}", homeworkId);
        return studentService.getQuestions(homeworkId);
    }

    @RequestMapping(value = "/getDoneAnswer", method = RequestMethod.POST)
    @ResponseBody
    public Object getDoneAnswer(Integer studentId, Integer homeworkId) {
        logger.info("学生ID:{},查询 QuestionID:{} 已提交答案", homeworkId);
        return studentService.getDoneAnswer(studentId, homeworkId);
    }


    @RequestMapping(value = "/submitOneAnswer", method = RequestMethod.POST)
    @ResponseBody
    public Object submitOneAnswer(Integer studentId, Integer homeworkId, String questionId, String type, String answer) {
        logger.info("学生ID:{},提交答案，HomeWorkId:{},QuestionID:{},Answer:{}", studentId, homeworkId, questionId, answer);
        return studentService.submitAnswer(studentId, homeworkId, questionId, type, answer);
    }

    @RequestMapping(value = "/getOneAnswer", method = RequestMethod.POST)
    @ResponseBody
    public Object getOneAnswer(Integer studentId, Integer homeworkId, String questionId, String type) {
        logger.info("学生ID:{},查看答案，HomeWorkId:{},QuestionID:{},Answer:{}", studentId, homeworkId);
        return studentService.getOneAnswer(studentId, homeworkId, questionId, type);
    }

    @RequestMapping(value = "/submitAllAnswer", method = RequestMethod.POST)
    @ResponseBody
    public Object submitAllAnswer(Integer studentId, Integer homeworkId) {
        logger.info("学生ID:{},已完成作业，HomeWorkId:{}", studentId, homeworkId);
        return studentService.submitAllAnswer(studentId, homeworkId);
    }

    @RequestMapping(value = "/getAllSubmit", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllSubmit(Integer studentId) {
        logger.info("学生ID:{},查看全部提交历史", studentId);
        return studentService.getAllSubmit(studentId);
    }

    @RequestMapping(value = "/getOneSubmit", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllSubmit(Integer studentId, Integer homeworkId) {
        logger.info("学生ID:{},查看 作业ID：{}提交情况", studentId, homeworkId);
        return studentService.getOneSubmitHomework(studentId, homeworkId);
    }


}
