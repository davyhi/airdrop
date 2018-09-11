package com.airdrop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 日志实体
 * @date 2018/8/23 09:53
 */
@Data
@Entity(name = "t_log")
public class Log {

    @ApiModelProperty("编号")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty("操作内容")
    private String content;

    @ApiModelProperty("操作人")
    private Integer userId;

    @ApiModelProperty("操作类型(0:正常,1:警告,2:错误)")
    private Integer type;

    @ApiModelProperty("操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createStamp;

    @ApiModelProperty("操作人")
    private String name;

    public Log() {

    }

    public Log(Integer userId, String content, Integer type) {
        this.content = content;
        this.userId = userId;
        this.type = type;
    }
}
