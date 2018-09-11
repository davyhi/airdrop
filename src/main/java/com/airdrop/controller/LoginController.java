package com.airdrop.controller;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.dto.UpdateDto;
import com.airdrop.dto._ResultDto;
import com.airdrop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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

    @ApiOperation("返回账户未登录信息")
    @GetMapping("/login")
    public _ResultDto login() {
        return new _ResultDto(CodeEnum.CODE_401.getCode(), "账户未登录");
    }

    @ApiOperation("账户登陆接口")
    @PostMapping("/login")
    public UpdateDto login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        return userService.login(username, password, session);
    }

}
