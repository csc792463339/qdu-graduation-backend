package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static qdu.graduation.backend.utils.FileUtil.saveImg;

@Service
public class FileUploadService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String saveImage(String img) {
        List<String> list = new ArrayList<>();
        String imgs[] = img.split("###");
        for (String content : imgs) {
            list.add(saveImg(content.split(",")[1]));
        }
        String paths = JSON.toJSONString(list);
        logger.info("图片存放地址:" + paths);
        return JSON.toJSONString(paths);
    }
}
