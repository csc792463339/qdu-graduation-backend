package qdu.graduation.backend.manager;


import okhttp3.Response;
import org.junit.Before;
import qdu.graduation.backend.utils.OkHttpUtil;

import java.io.IOException;

public class Test {

    public String localhost = "http://localhost:8080/";
    public String server = "http://47.94.154.27:8080/";
    public String domain = "";
    public int flag = 0;

    @Before
    public void init() {
        if (flag == 0) {
            domain = localhost;
        } else {
            domain = server;
        }
    }


    @org.junit.Test
    public void getApprovalList() throws IOException {
        String url = domain + "manager/approvalList";
        Response response = OkHttpUtil.get(url, null);
        System.out.println(response.body().string());
    }

    @org.junit.Test
    public void approval() throws IOException {
        String url = domain + "manager/approval";
        String body = "phone=13586081528";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }

    @org.junit.Test
    public void addStudent() throws IOException {
        String url = domain + "manager/reject";
        String body = "phone=111321";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }

    @org.junit.Test
    public void tStudent() throws IOException {
        String url = domain + "question/distribute";
        String body = "questionIds=111321";
        Response response = OkHttpUtil.post(url, null, body);
        System.out.println(response.body().string());
    }


}
