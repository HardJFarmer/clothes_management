package com.ccsu.clothesmanagement.interceptor;

import com.ccsu.clothesmanagement.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hua JFarmer
 */
public class UserManageIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session中取出当前访问用户
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        // //获取请求的路径
        // String requestURI = request.getRequestURI();
        if(user.getIsAdmin() == 1){

            return true;
        }else {
            // request.setAttribute("power_msg", "抱歉，你还不是管理员，不能访问用户管理页面！");
            // request.getRequestDispatcher("/main.html").forward(request,response);
            response.sendRedirect("/main.html");
            return false;
        }
    }
}
