package qdu.graduation.backend.registerAndLogin;


import okhttp3.Response;
import qdu.graduation.backend.utils.OkHttpUtil;

import java.io.IOException;

public class Test {

    @org.junit.Test
    public void sendsms() throws IOException {
        String url = "http://localhost:8080/register/sendsms";
        String body = "phone=321";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }

    @org.junit.Test
    public void checksms() throws IOException {
        String url = "http://localhost:8080/register/checkcode";
        String body = "phone=321&code=6786";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }

    @org.junit.Test
    public void addStudent() throws IOException {
        String url = "http://localhost:8080/register/add/student";
        String body = "phone=111321&name=78687&password=1234";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }


    @org.junit.Test
    public void addTeacher() throws IOException {
        String url = "http://localhost:8080/register/add/teacher";
        String body = "phone=111321&name=78687&password=1234";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }

    @org.junit.Test
    public void login() throws IOException {
        String url = "http://localhost:8080/login/";
        String body = "phone=321&password=1234";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }
}
