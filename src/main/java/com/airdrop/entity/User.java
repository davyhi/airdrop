package com.airdrop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户实体类
 * @date 2018/9/10 18:14
 */
@Entity(name = "t_user")
@Data
public class User implements Serializable {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 公司名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账户余额
     */
    private Integer money;

    /**
     * 是否禁用，0:false 正常，1：true禁用
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    @Column(name = "create_stamp")
    private Timestamp createStamp;

    public User() {

    }

    public User(Integer id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}
