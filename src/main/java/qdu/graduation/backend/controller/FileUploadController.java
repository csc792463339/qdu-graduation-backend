package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import qdu.graduation.backend.services.FileUploadService;


@Controller
@CrossOrigin
@RequestMapping(value = "/upload")
public class FileUploadController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FileUploadService fileUploadService;

    @RequestMapping(value = "/img", method = RequestMethod.POST)
    @ResponseBody
    public Object saveImg(String image) {
        logger.info("保存图片");
        return fileUploadService.saveImage(image.replace(" ", "+"));
    }


}
