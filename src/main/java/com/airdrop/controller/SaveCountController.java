package com.airdrop.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.airdrop.entity.User;
import com.airdrop.service.UserService;
import com.airdrop.util.CookieUtil2;
import com.airdrop.util.StringUtil;
import com.airdrop.vo.UserVo;


import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.airdrop.config.code.CodeEnum;
import com.airdrop.dto._ResultDto;
import com.airdrop.entity.MoneyHistory;
import com.airdrop.service.MoneyHistoryService;
import com.airdrop.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
public class SaveCountController {
//允许跨域访问
    @Autowired
    private MoneyHistoryService historyService;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    CookieUtil2 cookieUtil=new CookieUtil2();
    // 数据表里增加了after trigger, 因此无需自己再手动调用controller层的接口来实现把新增的Money的值插入到history表了, 该方法暂时作废不用.
    @ApiOperation("保存money的值到History表里边")
    @PostMapping("/saveCount")
    public String saveCount(@RequestParam Integer count, HttpServletRequest request, HttpSession session, HttpServletResponse reponse) {
        ServletContext app2 = request.getServletContext();
        String xixihaha = (String)app2.getAttribute("xixihaha");
        CookieUtil2 cookieUtil=new CookieUtil2();
        String cookie = cookieUtil.getCookieValue(request,"davy");
        if (cookie=="") {
             cookie = request.getHeader("Authorization");
        }
        UserVo user = TokenUtil.getUser(xixihaha);
        user.getId();

        MoneyHistory history = new MoneyHistory();
        history.setId(user.getId());
        history.setCount(count);
        historyService.insertMoney(history);
        return "none";
    }

    @ApiOperation("保存money的值到user表里边")
    @PostMapping("/saveUserCount")
    public String saveUserCount(@RequestParam Integer count, HttpServletRequest request){
        ServletContext app2 = request.getServletContext();
        String xixihaha = (String)app2.getAttribute("xixihaha");
        UserVo user = TokenUtil.getUser(xixihaha);
        user.getId();

        User user1 = new User();
        user1.setId(user.getId());
        user1.setMoney(count);
        userService.updateUserMoney(user1.getId(),user1.getMoney());
        return "none";
    }

    @ApiOperation("查看用户空投历史记录")
    @GetMapping("/checkUserCount")
    public String checkUserCount(HttpServletRequest request,HttpServletResponse response){
        ServletContext app3 = request.getServletContext();
        String xixihaha = (String)app3.getAttribute("xixihaha");
        UserVo user = TokenUtil.getUser(xixihaha);
        List UserCount = historyService.findHistoryCountByUserId(user.getId());

        JSONArray jsonArray2 = JSONArray.fromObject(UserCount);//将集合转换为json格式
        System.out.println(jsonArray2);
        String jsonString=jsonArray2.toString();//将json转换为字符串
        System.out.println(jsonString);
                return jsonString;

    }



    //暂时不用这个方法
    @PostMapping("/findCount")
    public String findCount(HttpServletRequest request) {

        ServletContext app3 = request.getServletContext();
        String xixihaha = (String)app3.getAttribute("xixihaha");
        UserVo user = TokenUtil.getUser(xixihaha);
        //返回前台接受
        Integer count = historyService.findCountByUserId(user.getId());
        return ""+count;
    }


    @GetMapping("/findBalance")
    public String findBalance(HttpServletRequest request) {

        ServletContext app3 = request.getServletContext();
        String xixihaha = (String)app3.getAttribute("xixihaha");
        UserVo user = TokenUtil.getUser(xixihaha);
        //返回前台接受
        if (user.getId()==null){
            return null;
        }
        String balance = this.userService.findBalanceByUserId(user.getId());
        return ""+balance;
    }

    //空投查询 分别根据手机号和邮箱都查查看
    @PostMapping("/findSpecialBalance")
    public String findSpecialBalance(HttpServletRequest request, String number){
         if(this.userService.findSpecialBalanceByPhone(number)!=null){
             return this.userService.findSpecialBalanceByPhone(number);
         }
         if(this.userService.findSpecialBalanceByEmail(number) !=null){

        return this.userService.findSpecialBalanceByEmail(number);
         }else{
             //如果没查询到,返回状态码 500
        return "500";
    }

}}
