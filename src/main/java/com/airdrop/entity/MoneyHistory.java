package com.airdrop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 余额历史纪录
 * @date 2018/9/11 11:05
 */
@Entity(name = "t_money_history")
@Data
public class MoneyHistory implements Serializable {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 历史内容
     */
    private String content;

    /**
     * 余额
     */
    private Integer money;

    /**
     * 0：加，1：减
     */
    @Column(name = "plus_or_minus")
    private Integer plusOrMinus;

    /**
     * 创建时间
     */
    @Column(name = "create_stamp")
    private Timestamp createStamp;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Integer userId;

}
