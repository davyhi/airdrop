package com.airdrop.vo;

import com.airdrop.entity.Privileges;
import com.airdrop.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户值对象
 * @date 2018/9/11 11:34
 */
@Data
public class UserVo implements Serializable {

    private Integer id;

    private String name;

    private String phone;

    private String email;

    private String token;

    private List<Privileges> pris;

    private List<Role> roles;

    public UserVo(Integer id, String name, String email, String phone, List<Privileges> privileges, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pris = privileges;
        this.roles = roles;
    }

}
