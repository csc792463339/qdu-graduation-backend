package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.entity.Classes;
import qdu.graduation.backend.services.ClassesService;

import javax.annotation.Resource;

/**
 * Created by Jay on 2018/4/11.
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/classes")
public class ClassesController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ClassesService classesService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Object list(Integer teacherId) {
        logger.info("获取该老师管理的所有班级"+teacherId);
        return classesService.getClassesById(teacherId);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(Classes classes){
        logger.info("插入班级:"+classes.toString());
        return classesService.insertClassesByTeacherId(classes);
    }
}
