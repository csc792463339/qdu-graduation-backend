package qdu.graduation.backend.registerAndLogin;


import okhttp3.Response;
import org.junit.Before;
import qdu.graduation.backend.utils.OkHttpUtil;

import java.io.IOException;

public class Test {

    public String localhost = "http://localhost:8080/";
    public String server = "http://47.94.154.27:8080/";
    public String domain = "";
    public int flag = 1;

    @Before
    public void init() {
        if (flag == 0) {
            domain = localhost;
        } else {
            domain = server;
        }
    }


    @org.junit.Test
    public void sendsms() throws IOException {
        String url = domain + "register/sendsms";
        String body = "phone=32001";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }

    @org.junit.Test
    public void checksms() throws IOException {
        String url = domain + "register/checkcode";
        String body = "phone=321&code=6786";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }

    @org.junit.Test
    public void addStudent() throws IOException {
        String url = domain + "register/add/student";
        String body = "phone=111321&name=78687&password=1234";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }


    @org.junit.Test
    public void addTeacher() throws IOException {
        String url = domain + "register/add/teacher";
        String body = "phone=111321&name=78687&password=1234";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }

    @org.junit.Test
    public void login() throws IOException {
        String url = domain + "login/";
        String body = "phone=321&password=1234";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }
}
