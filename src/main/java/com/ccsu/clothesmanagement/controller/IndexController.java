package com.ccsu.clothesmanagement.controller;

import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 处理登录和页面跳转逻辑
 * @author Hua JFarmer
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    /**
     * 初始界面为登录界面
     * @return
     */
    @GetMapping("/")
    public String toLogin(){
        return "/login";
    }

    /**
     * 处理重定向请求到首页
     * @return
     */
    @GetMapping("/main.html")
    public String toIndex(){
        return "main";
    }

    /**
     * 实现登录
     * @param user 获取表单数据账号或者密码
     * @param model 响应数据
     * @param session  session
     * @return
     */
    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session){
        User loginUser = userService.login(user);
        if(loginUser != null){
            session.setAttribute("USER_SESSION", loginUser);
            return "redirect:/main.html";
        }else {
            model.addAttribute("msg", "用户名或密码错误");
            return "/login";
        }
    }

    /**
     * 注销
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
            HttpSession session = request.getSession();
            //销毁Session
            session.invalidate();
            return "redirect:/";
    }
}
