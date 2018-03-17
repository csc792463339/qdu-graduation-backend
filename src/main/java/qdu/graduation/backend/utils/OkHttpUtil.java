package qdu.graduation.backend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by chensicao on 2017/11/16.
 */
public class OkHttpUtil {
    public static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    private static OkHttpClient okHttpClient = null;

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            // 初始化OkHttpClient, 一个实例的请求共享连接池
            try {
                okHttpClient = new OkHttpClient.Builder().
                        retryOnConnectionFailure(true).
                        proxy(null).
                        connectTimeout(30, TimeUnit.SECONDS).
                        writeTimeout(30, TimeUnit.SECONDS).
                        readTimeout(30, TimeUnit.SECONDS).
                        build();
            } catch (Exception e) {
                logger.error("getOkHttpClient() Error : " + e);
            }
        }
        return okHttpClient;
    }

    public static Response get(String url, Map<String, String> headers) {
        logger.info("GET:" + url);
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.header("Connection", "close");
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                builder.addHeader(header.getKey(), header.getValue());
            }
        }
        Response response = null;
        Request request = builder.build();
        try {
            response = getOkHttpClient().newCall(request).execute();
        } catch (Exception e) {
            logger.error("网络异常: " + e);
        }
        return response;
    }

    public static Response post(String url, Map<String, String> headers, String body) {
        logger.info("POST:" + url);
        Request.Builder builder = new Request.Builder();
        Response response = null;
        builder.url(url);
        builder.header("Connection", "close");
        MediaType mediaType = null;
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                builder.addHeader(header.getKey(), header.getValue());
            }
        }
        JSONObject json = null;
        try {
            json = JSON.parseObject(body);
        } catch (Exception e) {
            // logger.error("JSON.parseObject(body) error!");
        }
        if (json != null) {
            mediaType = MediaType.parse("application/json; charset=utf-8");
        } else {
            mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        }
        Request request = builder.post(RequestBody.create(mediaType, body)).build();
        try {
            response = getOkHttpClient().newCall(request).execute();
        } catch (Exception e) {
            logger.error("网络异常: " + e);
        }
        return response;
    }

    public static void restart() {
        okHttpClient = null;
    }


}
