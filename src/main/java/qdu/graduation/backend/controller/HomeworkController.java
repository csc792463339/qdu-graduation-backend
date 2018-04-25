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
import qdu.graduation.backend.support.StatusCode;

import javax.annotation.Resource;
import java.beans.SimpleBeanInfo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Object list(String teacherId) {
        logger.info("获取老师" + teacherId + "题目集列表");
        String res = homeworkService.selectAllHomework(Integer.parseInt(teacherId));
        logger.info("获取题目集结果" + res);
        return res;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Object detail(String homeworkId) {
        logger.info("获取" + homeworkId + "的详情");
        return homeworkService.selectByPrimaryKey(Integer.parseInt(homeworkId));
    }

    @RequestMapping(value = "/distribute", method = RequestMethod.POST)
    @ResponseBody
    public Object distribute(String questionid, String classlist, String deadline) {
        logger.info("分发：班级信息" + classlist);
        logger.info("分发：习题信息" + questionid);
        logger.info("最晚时间：" + deadline);
        Date d = new Date();
        try {
            d = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(deadline);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        List<String> classesList = extractMessage(classlist);
        List<String> questionsList = extractMessage(questionid);
        homeworkService.distributeClass(questionsList.get(0), classesList, d);
        return homeworkService.distribute(questionsList.get(0), classesList,d);
    }

    /**
     * 提取中括号中内容，忽略中括号中的中括号
     *
     * @param msg
     * @return
     */
    public List<String> extractMessage(String msg) {
        List<String> list = new ArrayList<String>();
        int start = 0;
        int startFlag = 0;
        int endFlag = 0;
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == '【') {
                startFlag++;
                if (startFlag == endFlag + 1) {
                    start = i;
                }
            } else if (msg.charAt(i) == '】') {
                endFlag++;
                if (endFlag == startFlag) {
                    list.add(msg.substring(start + 1, i));
                }
            }
        }
        return list;
    }
}
