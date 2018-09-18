package com.airdrop.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 权限实体类
 * @date 2018/7/17 10:48
 */
@Data
@Entity(name = "t_privileges")
public class Privileges implements Serializable {

    /**
     * 编号
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限描述
     */
    @Column(name = "`describe`")
    private String describe;

    /**
     * 访问地址
     */
    private String url;

    /**
     * 上级权限id
     */
    private Long pid;

    /**
     * 权限归属id
     */
    private Long ppid;

    /**
     * 请求类型
     */
    private String type;

    /**
     * 权限code
     */
    private String code;

    /**
     * 存储该权限包含哪些角色可访问
     */
    @Transient
    private String roleCodes;

    /**
     * 存储子权限
     */
    @Transient
    private List<Privileges> children = new ArrayList<Privileges>(50);

    public Privileges() {

    }

    public Privileges(String url, String type) {
        this.url = url;
        this.type = type;
    }
}
