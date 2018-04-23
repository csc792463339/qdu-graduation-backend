package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.StudentApprovalService;

import javax.annotation.Resource;

/**
 * Created by Jay on 2018/4/22.
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/teacher")
public class TeacherController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StudentApprovalService studentApprovalService;

    @RequestMapping(value = "/studentApprovalList", method = RequestMethod.POST)
    @ResponseBody
    public Object getApprovalList(String teacherId) {
        logger.info("获取老师" + teacherId + "的学生申请列表");
        return studentApprovalService.getApprovalList(teacherId);
    }

    @RequestMapping(value = "/studentApprovalPass", method = RequestMethod.POST)
    @ResponseBody
    public Object studentPass(String classId, String studentId) {
        logger.info("通过" + studentId + "同学" + classId + "班级的申请");
        return studentApprovalService.studentPass(classId, studentId);
    }

    @RequestMapping(value = "/studentApprovalReject", method = RequestMethod.POST)
    @ResponseBody
    public Object studentReject(String classId, String studentId) {
        logger.info("拒绝" + studentId + "同学" + classId + "班级的申请");
        return studentApprovalService.studentReject(classId, studentId);
    }

}