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
}
