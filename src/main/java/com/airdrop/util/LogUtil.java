package com.airdrop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 日志打印
 * @date 2018/8/16 14:07
 */
public class LogUtil {

    private static Logger log = LoggerFactory.getLogger(LogUtil.class);

    // 定义日志类型key
    public static final int SUCCESS = 0;
    public static final int WARN = 1;
    public static final int ERROR = 2;

    // true开启debug日志，false：关闭debug日志
    private static boolean isBol = true;

    // 打印异常信息
    public static void pringException(Exception e) {
        if (isBol) e.printStackTrace();
    }

    // 打印信息
    public static void pringError(String content) {
        log.error(content);
    }

    // 打印信息
    public static void pringInfo(String content) {
        log.info(content);
    }

    // 打印信息
    public static void pringWarn(String content) {
        log.warn(content);
    }

}