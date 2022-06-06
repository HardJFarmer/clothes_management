package com.ccsu.clothesmanagement.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 */
public class LoginIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录检查
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("USER_SESSION");
        if(loginUser != null){
            return true;
        }
        request.setAttribute("msg", "请先登录");
        request.getRequestDispatcher("/").forward(request,response);
        return false;
    }
}
