package qdu.graduation.backend.services;

import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import qdu.graduation.backend.utils.OkHttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SendSmsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String apiUrl = "http://sms.106jiekou.com/utf8/sms.aspx";
    private Map<String, String> header = new HashMap<>();

    public boolean sendCode(String phone, String code) throws IOException {
        logger.info(phone + ":正在发送注册短信验证码");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        String smsContent = java.net.URLEncoder.encode("您的验证码是：" + code + "。请不要把验证码泄露给其他人。如非本人操作，可不用理会！", "utf-8");
        String postData = "account=csc792463339&password=test123&mobile=" + phone + "&content=" + smsContent;
        Response response = OkHttpUtil.post(apiUrl, header, postData);
        String res = response.body().string();
        if (res.equals("100")) {
            logger.info(phone + ":Send code:" + code + ",发送成功");
            return true;
        } else {
            logger.info(phone + ":Send code:" + code + ",发送失败,结果返回:" + res);
            return false;
        }
    }

    public boolean notice(String phone, String taskName, String teacherName, String className) throws IOException {
        logger.info(phone + ":正在发送新作业通知短信");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        String content = "您有新的订单：" + taskName + "，收货人：" + teacherName + "，电话：" + className + "，请及时确认订单！";
        String smsContent = java.net.URLEncoder.encode(content, "utf-8");
        String postData = "account=csc792463339&password=test123&mobile=" + phone + "&content=" + smsContent;
        Response response = OkHttpUtil.post(apiUrl, header, postData);
        String res = response.body().string();
        if (res.equals("100")) {
            logger.info(phone + ":Send:" + content + ",发送成功");
            return true;
        } else {
            logger.info(phone + ":Send:" + content + ",发送失败,结果返回:" + res);
            return false;
        }
    }

    public boolean warn(String phone, String taskName, String teacherName, String className) throws IOException {
        logger.info(phone + ":正在发送作业到期提醒");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        String content = "您的" + taskName + "，单号：" + className + "，状态：快到期了。客服电话" + teacherName + "，如需帮助请联系！";
        String smsContent = java.net.URLEncoder.encode(content, "utf-8");
        String postData = "account=csc792463339&password=test123&mobile=" + phone + "&content=" + smsContent;
        Response response = OkHttpUtil.post(apiUrl, header, postData);
        String res = response.body().string();
        if (res.equals("100")) {
            logger.info(phone + ":Send:" + content + ",发送成功了");
            return true;
        } else {
            logger.info(phone + ":Send:" + content + ",发送失败了,结果返回:" + res);
            return false;
        }
    }

}

/*状态码		说明
100			发送成功
101			验证失败
102			手机号码格式不正确
103			会员级别不够
104			内容未审核
105			内容过多
106			账户余额不足
107			Ip受限
108			手机号码发送太频繁，请换号或隔天再发
109			帐号被锁定
110			手机号发送频率持续过高，黑名单屏蔽数日
120			系统升级
*/
