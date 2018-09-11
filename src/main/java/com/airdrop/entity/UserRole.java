package com.airdrop.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户角色实体类
 * @date 2018/7/17 10:48
 */
@Data
@Entity(name = "t_user_role")
public class UserRole {

    /**
     * 编号
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     **/
    @Column(name = "u_id")
    private Integer uId;

    /**
     * 角色id
     */
    @Column(name = "r_id")
    private Long rId;

    public UserRole(Integer userid) {
        this.uId = userid;
    }

    public UserRole(Integer userid, Long roleid) {
        this.uId = userid;
        this.rId = roleid;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }
}
