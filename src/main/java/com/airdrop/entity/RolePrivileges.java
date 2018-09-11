package com.airdrop.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 角色权限实体类
 * @date 2018/7/17 10:48
 */
@Data
@Entity(name = "t_role_privileges")
public class RolePrivileges {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色编号
     **/
    @Column(name = "r_id")
    private Long rId;

    /**
     * 权限编号
     **/
    @Column(name = "p_id")
    private Long pId;

    public RolePrivileges(Long rid, Long pid) {
        this.rId = rid;
        this.pId = pid;
    }
}
