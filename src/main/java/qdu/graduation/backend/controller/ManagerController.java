package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.ClassesService;
import qdu.graduation.backend.services.TeacherApprovalService;
import qdu.graduation.backend.services.TeacherInfoService;

import javax.annotation.Resource;

@Controller
@CrossOrigin
@RequestMapping("/manager")
public class ManagerController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TeacherApprovalService teacherApprovalService;

    @Resource
    private TeacherInfoService teacherInfoService;

    @Resource
    private ClassesService classesService;

    @RequestMapping(value = "/approvalList", method = RequestMethod.GET)
    @ResponseBody
    public Object getApprovalList() {
        logger.info("管理员-获取教师审核列表");
        return teacherApprovalService.getApprovalList();
    }


    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    @ResponseBody
    public Object approval(String phone) {
        logger.info("管理员-通过" + phone + "教师申请");
        return teacherApprovalService.accectApproval(phone);
    }


    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    @ResponseBody
    public Object addAcount(String phone) {
        logger.info("管理员-拒绝" + phone + "教师申请");
        return teacherApprovalService.rejectApproval(phone);
    }


    @RequestMapping(value = "/teacherInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object teacherInfo() {
        logger.info("管理员-查看教师信息");
        return teacherInfoService.getAllTecharAndClassInfo();
    }

    @RequestMapping(value = "/classInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object ClassInfo() {
        logger.info("管理员-查看班级信息");
        return classesService.getAllClassAndStudentCount();
    }

    @RequestMapping(value = "/studentInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object StudentInfo() {
        logger.info("管理员-查看学生信息");
        return "";
    }

    @RequestMapping(value = "/homeWorkInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object HomeWorkInfo() {
        logger.info("管理员-查看作业信息");
        return "";
    }

}
