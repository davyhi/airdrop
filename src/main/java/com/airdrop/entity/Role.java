package com.airdrop.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 角色实体类
 * @date 2018/7/17 10:48
 */
@Data
@Entity(name = "t_role")
public class Role {

    /**
     * 编号
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色code
     */
    private String code;

    /**
     * 角色描述
     */
    @Column(name = "`describe`")
    private String describe;

    /**
     * 权限id
     *
     * @Transient jpa 会忽略这个字段，不会持久化，不会orm映射
     */
    @Transient
    private Long[] pids;
}
