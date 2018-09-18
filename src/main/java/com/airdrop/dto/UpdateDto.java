package com.airdrop.dto;

import java.io.Serializable;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 更新数据返回类
 * @date 2018/9/10 18:52
 */

public class UpdateDto extends _ResultDto implements Serializable {


    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public UpdateDto() {

    }

    public UpdateDto(Object data) {
        this.data = data;
    }


}
