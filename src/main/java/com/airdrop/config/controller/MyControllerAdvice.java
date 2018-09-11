package com.airdrop.config.controller;

import com.airdrop.config.code.CodeEnum;
import com.airdrop.config.exception.ServiceException;
import com.airdrop.dto._ResultDto;
import com.airdrop.util.LogUtil;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 异常统一管理
 * @date 2018/8/22 11:42
 */
@ControllerAdvice
public class MyControllerAdvice {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
//    @InitBinderdto
//    public void initBinder(WebDataBinder binder) {
//        System.err.println("哈哈哈哈哈哈哈哈哈哈哈哈哈，我进来了");
//    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    /*@ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "Magical Sam");
    }*/


    /**
     * 全局异常捕捉处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public _ResultDto errorHandler(Exception e) {
        LogUtil.pringException(e);
        return new _ResultDto(CodeEnum.CODE_500.getCode(), CodeEnum.CODE_500.getMessage());
    }

    /**
     * 全局异常捕捉处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public _ResultDto NoHttp(HttpRequestMethodNotSupportedException e) {
        LogUtil.pringException(e);
        return new _ResultDto(CodeEnum.CODE_404.getCode(), CodeEnum.CODE_404.getMessage());
    }

    /**
     * 自定义异常捕捉处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public _ResultDto errorHandler(ServiceException e) {
        return new _ResultDto(e.getErrorCode(), e.getMessage());
    }

}