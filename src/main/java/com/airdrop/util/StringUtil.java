package com.airdrop.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 字符串工具类
 * @date 2018/9/10 18:40
 */
public class StringUtil {


    /**
     * 字符串等于空
     *
     * @param val
     * @return
     */
    public static boolean isEmpty(String val) {
        return (null == val || val.equals(""));
    }

    /**
     * 字符串不等于空
     *
     * @param val
     * @return
     */
    public static boolean isNotEmpty(String val) {
        return (null != val && !val.equals(""));
    }


    /**
     * int转换
     *
     * @param val
     * @return
     */
    public static Integer parseInt(Object val) {
        if (val != null) {
            if (val instanceof Integer) {
                return (int) val;
            } else {
                return Integer.parseInt(val.toString());
            }
        }
        return null;
    }

    /**
     * long转换
     *
     * @param val
     * @return
     */
    public static Long parseLong(Object val) {
        if (val != null) {
            if (val instanceof Long) {
                return (long) val;
            } else {
                return Long.valueOf(val.toString());
            }
        }
        return null;
    }

    /**
     * 字符串转换
     *
     * @param val
     * @return
     */
    public static String parseStr(Object val) {
        return val != null ? val.toString() : null;
    }

    /**
     * 将date转换成年月日时分秒
     *
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static void main(String[] args) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String format = getDateStr(date);
        System.err.println(format);
    }

    /**
     * 从左边截取字符串
     *
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String subLeft(String str, int start, int end) {
        return str.substring(start, end != -1 ? end : str.length());
    }

    /**
     * 从左边截取字符串
     *
     * @param str
     * @param start
     * @param end   根据字符串查找最后下标
     * @return
     */
    public static String subLeft(String str, int start, String end) {
        int index = str.indexOf(end);
        return str.substring(start, index != -1 ? index : str.length());
    }


}
