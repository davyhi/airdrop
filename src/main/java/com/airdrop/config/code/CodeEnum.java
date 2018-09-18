package com.airdrop.config.code;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: code定义
 * @date 2018/7/6 11:56
 */
public enum CodeEnum {

    CODE_200(200, ""), CODE_400(400, "请求参数错误"), CODE_401(401, "权限认证失败"), CODE_403(403, "权限不足"), CODE_404(404, "请求资源不存在"), CODE_409(409, "请求资源冲突"), CODE_415(415, "请求格式错误"), CODE_423(423, "请求资源被锁定"), CODE_500(500, "服务器内部错误"), CODE_501(501, "请求方法不存在"), CODE_503(503, "服务暂时不可用");

    private int code;

    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

