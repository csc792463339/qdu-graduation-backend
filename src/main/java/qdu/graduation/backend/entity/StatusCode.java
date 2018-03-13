package qdu.graduation.backend.entity;

public enum StatusCode {
    /*全局统一状态码*/

    //成功-2XX
    success("200", "成功"),
    sendSms("201", "短信验证码已发送成功"),

    //错误-3XX
    error("300", "错误"),
    hasSendSms("301", "短信验证码已发送，请等待！"),
    smsCodeInvalid("302", "短信验证码已失效，请重新发送！"),

    //失败-4XX
    failed("400", "失败！"),
    phoneHasRegister("401", "该手机号已注册！"),
    codeWrong("402", "短信验证码错误！");

    private String msg;
    private String code;

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        String res = "{\"code\":\"" + this.getCode() + "\",\"msg\":\"" + this.getMsg() + "\"}";
        return res;
    }
}
