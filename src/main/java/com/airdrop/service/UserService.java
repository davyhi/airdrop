package com.airdrop.service;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto.UpdateDto;
import com.airdrop.entity.Privileges;
import com.airdrop.entity.Role;
import com.airdrop.entity.User;
import com.airdrop.repository.PrivilegesRepository;
import com.airdrop.repository.RoleRepository;
import com.airdrop.repository.UserRepository;
import com.airdrop.util.LogUtil;
import com.airdrop.util.StringUtil;
import com.airdrop.util.TokenUtil;
import com.airdrop.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 用户业务层
 * @date 2018/9/10 18:36
 */
@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegesRepository privilegesRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LogService logService;

    /**
     * 登陆方法
     *
     * @param username
     * @param password
     * @return
     */
    public UpdateDto login(String username, String password, HttpSession session) {
        // 获取用户信息
        User user = userRepository.findByUsername(username);
        // 判断用户、密码是否正确
        if (null == user || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "用户名或密码输入错误，登录失败!");
        }
        // 判断用户是否被禁用
        if (user.getEnable()) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), "账户被禁用，登录失败，请联系管理员!");
        }
        // 设置用户登陆信息
        String token = setUserLoginInfo(session, user);
        // 更新日志
        logService.insertLog(user.getId(), user.getName() + "登陆系统", LogUtil.SUCCESS);
        return new UpdateDto(new HashMap<String, String>() {
            {
                this.put(TokenUtil.TOKEN, token);
            }
        });
    }

    /**
     * 设置用户登陆信息保存到session
     *
     * @param session
     * @param user
     * @return 返回token
     */
    private String setUserLoginInfo(HttpSession session, User user) {
        // 获取用户权限相关信息，
        List<Role> roles = roleRepository.findByUserId(user.getId());
        List<Privileges> pris = null;
        if (roles.size() > 0) {
            // 根据用户的角色去获取权限
            StringBuffer sb = new StringBuffer();
            for (Role r : roles) {
                sb.append(r.getId()).append(',');
            }
            pris = privilegesRepository.findByROles(sb.substring(0, sb.length() - 1));
        }
        // 生成token,保存用户信息到session
        String token = TokenUtil.createToken(new UserVo(user.getId(), user.getName(), user.getEmail(), user.getPhone(), pris, roles));
        session.setAttribute(token, true);
        // 设置登陆有效时长 1小时
        session.setMaxInactiveInterval(60 * 60);
        return token;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    public UpdateDto register(User user) {
        // 校验（手机号/邮箱），密码是否是空数据，校验邮箱或者密码是否已存在
        checkUser(user);
        // 密码加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword().trim()));
        // 注册操作
        user = userRepository.saveAndFlush(user);
        // 判断是否添加成功
        if (null == user || user.getId() == null) {
            throw new ServiceException(CodeEnum.CODE_500.getCode(), CodeEnum.CODE_500.getMessage());
        }
        return new UpdateDto(user);
    }

    /**
     * 校验（手机号/邮箱），密码是否是空数据
     *
     * @param user
     * @return
     */
    private void checkUser(User user) {
        if (null == user || ((StringUtil.isEmpty(user.getPhone()) && StringUtil.isEmpty(user.getEmail())) && StringUtil.isEmpty(user.getPassword()))) {
            throw new ServiceException(CodeEnum.CODE_400.getCode(), CodeEnum.CODE_400.getMessage());
        } else if (StringUtil.isNotEmpty(user.getEmail())) {    // 校验邮箱是否已存在
            if (userRepository.findByUsername(user.getEmail()) != null) {
                throw new ServiceException(CodeEnum.CODE_400.getCode(), "邮箱已存在");
            }
        } else if (StringUtil.isNotEmpty(user.getPhone())) {    // 校验手机号是否已存在
            if (userRepository.findByUsername(user.getPhone()) != null) {
                throw new ServiceException(CodeEnum.CODE_400.getCode(), "手机号已存在");
            }
        }
    }


    /**
     * 不适用框架登陆
     *
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
