package qdu.graduation.backend.utils;

import java.util.Random;

public class CodeUtil {

    public static String randomCode() {

        String sources = "0123456789";
        Random rand = new Random();
        StringBuffer code = new StringBuffer();
        for (int j = 0; j < 4; j++) {
            code.append(sources.charAt(rand.nextInt(9)) + "");
        }
        return code.toString();
    }
}
