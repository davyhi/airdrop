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


    /**
     * 用户编号
     */
    @Column(name = "count")
    private Integer count;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public Integer getMoney() {
        return money;
    }


    public void setMoney(Integer money) {
        this.money = money;
    }


    public Integer getPlusOrMinus() {
        return plusOrMinus;
    }


    public void setPlusOrMinus(Integer plusOrMinus) {
        this.plusOrMinus = plusOrMinus;
    }


    public Timestamp getCreateStamp() {
        return createStamp;
    }


    public void setCreateStamp(Timestamp createStamp) {
        this.createStamp = createStamp;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getCount() {
        return count;
    }


    public void setCount(Integer count) {
        this.count = count;
    }



}
