package qdu.graduation.backend.utils;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileUtil {

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
            String path = "D:\\" + name;
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
}
