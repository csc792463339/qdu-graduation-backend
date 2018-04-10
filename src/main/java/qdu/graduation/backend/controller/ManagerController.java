package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.TeacherApprovalService;

import javax.annotation.Resource;

@Controller
@CrossOrigin
@RequestMapping("/manager")
public class ManagerController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TeacherApprovalService teacherApprovalService;

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

}
