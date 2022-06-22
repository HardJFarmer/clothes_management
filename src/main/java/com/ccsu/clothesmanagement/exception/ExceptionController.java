package com.ccsu.clothesmanagement.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Hua JFarmer
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * 监听数学运算异常
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView handleArithException(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/500");
        modelAndView.addObject("message","你好像输入了糟糕的数字");
        modelAndView.addObject("status","回到首页");
        return modelAndView;
    }

    /**
     * 监听空指针和数组越界错误
     * @return
     */
    @ExceptionHandler({NullPointerException.class, IndexOutOfBoundsException.class})
    public ModelAndView handleNullAndIndexOutException(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/500");
        modelAndView.addObject("message","糟糕，发生了未知错误");
        modelAndView.addObject("status","回到首页");
        return modelAndView;
    }
}
