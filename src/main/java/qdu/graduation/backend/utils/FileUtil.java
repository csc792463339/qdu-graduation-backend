package qdu.graduation.backend.utils;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileUtil {

    public static String saveImg(String imgStr) {
        System.out.println("******");
        System.out.println(imgStr);
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            String path = "D:\\" + System.currentTimeMillis() + ".png";
            String imgFilePath = path;
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
