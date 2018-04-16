package qdu.graduation.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import qdu.graduation.backend.services.FileService;

import javax.servlet.http.HttpServletResponse;


@Controller
@CrossOrigin
@RequestMapping(value = "/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    @ResponseBody
    public Object saveImg(String image) {
        logger.info("保存图片");
        return fileService.saveImage(image.replace(" ", "+"));
    }

    @RequestMapping(value = "/download/{name}", method = RequestMethod.GET)
    public void download(HttpServletResponse res, @PathVariable("name") String name) {
        logger.info("下载文件:" + name);
        fileService.downloadFile(res, name);
    }
}
