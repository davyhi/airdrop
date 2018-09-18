package com.airdrop.controller;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.dto.UpdateDto;
import com.airdrop.dto._ResultDto;
import com.airdrop.service.UserService;
import com.airdrop.util.CookieUtil2;
import com.airdrop.util.SessionUtil;
import com.airdrop.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 登陆接口控制
 * @date 2018/9/11 11:51
 */
@Api(description = "登陆Api接口")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

  CookieUtil2 cookieUtil = new CookieUtil2();

    @ApiOperation("返回账户未登录信息")
    @GetMapping("/login")
    public _ResultDto login() {
        return new _ResultDto(CodeEnum.CODE_401.getCode(), "账户未登录");
    }

    @ApiOperation("账户登陆接口")
    @PostMapping("/login")
    public int login(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {

        UpdateDto login = userService.login(username, password, session, request, response);
        if(login.getCode()==200){

            return 200;
        }else{
            return 400;
        }
    }

    @ApiOperation("账户注销登陆")
    @PostMapping("/logout")
    public _ResultDto logout(HttpServletRequest request) {
        // 获得token
        String token = request.getHeader(TokenUtil.TOKEN);
        // 校验token是否有效
        if (null != token && SessionUtil.checkUser(token, request.getSession())) {
            request.getSession().removeAttribute(token);
        }
        return new _ResultDto();
    }

}
