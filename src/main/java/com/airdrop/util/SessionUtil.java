package com.airdrop.util;

import javax.servlet.http.HttpSession;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: session管理
 * @date 2018/9/11 10:23
 */
public class SessionUtil {


    /**
     * 检测用户的token是否还在有效状态
     *
     * @param token
     * @return true:通过，false不通过
     */
    public static boolean checkUser(String token, HttpSession session) {
        return session.getAttribute(token) != null;
    }


}
