package com.airdrop.controller;

import com.airdrop.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zengdavy
 * @date: 2018/9/14
 * @description:
 */

@RestController
public class CheckBalanceController {

    @Autowired
    private UserService userService;

}
