package qdu.graduation.backend.services;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import qdu.graduation.backend.utils.RegexUtil;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class FileService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static String rootPath = "";

    static {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.contains("Windows") || osName.contains("windows")) {
            rootPath = "D://file//";
        } else {
            rootPath = "/home/file/";
        }
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


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

    public String saveFile(MultipartFile file) {
        String name = file.getOriginalFilename();
        String type = RegexUtil.findOne(name, "(?<=\\.)\\w+$");
        if (StringUtils.isEmpty(type)) {
            name = name + System.currentTimeMillis();
        } else {
            name = name.replaceAll("." + type, "") + System.currentTimeMillis() + "." + type;
        }

        String path = rootPath + name;
        File file1 = new File(path);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file1);
                fileOutputStream.write(file.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("文件存放地址:" + path);
        return name;
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
            bis = new BufferedInputStream(new FileInputStream(new File(rootPath + name)));
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


    public static String saveImg(String imgStr) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            String name = System.currentTimeMillis() + ".png";
            String path = rootPath + name;
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

    }

}
