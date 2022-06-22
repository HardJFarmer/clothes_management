package com.ccsu.clothesmanagement.controller;

import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.entity.Result;
import com.ccsu.clothesmanagement.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * @author Hua JFarmer
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user.html")
    public String goUserPage(){
        return "/user";
    }

    @GetMapping("/user")
    @ResponseBody
    public Result getUserList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "8") Integer pageSize) {
        PageInfo<User> userList = userService.getUserList(pageNum, pageSize);
        return Result.success().add("users", userList);
    }

    @PostMapping("/adduser")
    @ResponseBody
    public Result addUser(User user){
        userService.addUser(user.getUsername(),user.getPassword());
        return Result.success();
    }

    @PostMapping("/updateuser")
    @ResponseBody
    public Result updateUser(User user){
        userService.updateUserById(user);
        return Result.success();
    }

    @DeleteMapping("/updateuser/{id}")
    @ResponseBody
    public Result deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return Result.success();
    }

    @PostMapping("/userbyidnamestatus")
    @ResponseBody
    public Result selectUserByUseridAndUserUsernameAndUserStatus(Integer pageNum, Integer pageSize,User user){
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 8;
        }
        PageInfo<User> pageInfo=userService.selectUserByUseridAndUserUsernameAndUserStatus
                (pageNum,pageSize,user.getUserId(),user.getUsername(),user.getUserStatus());
        return Result.success().add("users",pageInfo);
    }

    @PostMapping("/modifyPassword")
    @ResponseBody
    public Result modifyPassword(String password1, String password2, HttpSession session){
        String passwordReg = "^[0-9a-zA-Z]{6,12}$";
        if (!password1.matches(passwordReg) || !password2.matches(passwordReg)){
            return Result.fail().add("msg_va", "请规范密码输入！");
        }
        if (!password1.equals(password2)){
            return Result.fail().add("msg_va", "两次输入密码不一致！");
        }
        User user = new User();
        user.setPassword(password1);
        //获取当前用户id
        User userSession = (User) session.getAttribute("USER_SESSION");
        //修改密码
        user.setUserId(userSession.getUserId());
        userService.updateUserById(user);
        return Result.success().add("success", "修改密码成功请重新登录！");
    }

}
