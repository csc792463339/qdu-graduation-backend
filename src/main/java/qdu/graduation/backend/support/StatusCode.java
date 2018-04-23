package qdu.graduation.backend.support;

public enum StatusCode {
    /*全局统一状态码*/

    //成功-2XX
    success("200", "成功"),
    sendSms("201", "短信验证码已发送成功"),
    waitApproval("202", "提交成功,等待管理员审核"),
    approval("203", "已通过"),
    reject("204", "已拒绝"),

    //错误-3XX
    error("300", "错误"),
    hasSendSms("301", "短信验证码已发送，请等待60s！"),
    smsCodeInvalid("302", "短信验证码已失效，请重新发送！"),

    //失败-4XX
    failed("400", "失败！"),
    phoneHasRegister("401", "该手机号已注册！"),
    codeWrong("402", "短信验证码错误！"),
    passWrong("403", "密码错误！"),
    userNotExist("404", "用户不存在！"),
    sendSmsFailed("405", "短信发送失败!"),

    //题库
    questionGetHomeWorkFailed("1107", "获取习题集失败"),
    questionGetHomeWorkSuccess("1106", "获取习题集成功"),
    questionInsertHomeWorkFailed("1105", "插入习题集失败"),
    questionInsertHomeWorkSuccess("1104", "插入习题集成功"),
    questionInsertFailed("1103", "插入题库失败"),
    questionInsertSuccess("1102", "插入题库成功"),
    questionSuccess("1100", "获取题库成功"),
    questionFailed("1101", "题库为空");

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
