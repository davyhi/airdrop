package com.airdrop.dto;

import com.airdrop.config.code.CodeEnum;

import java.io.Serializable;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 响应类
 * @date 2018/7/6 11:53
 */
public class _ResultDto implements Serializable {

    private String message = "";

    private int code = CodeEnum.CODE_200.getCode();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public _ResultDto() {

    }

    public _ResultDto(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }
}
