package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static qdu.graduation.backend.utils.FileUtil.saveImg;

@Service
public class FileService {

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

    public void downloadFile(HttpServletResponse res, String name) {
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + name);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("d://" + name)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
