package com.ccsu.clothesmanagement;

import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.service.UserService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testLogin() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");

        User login = userService.login(user);
        System.out.println(login);
    }

    @Test
    void testGetUserList(){
        PageInfo<User> userList = userService.getUserList(1, 1);
        System.out.println(userList);
    }

    @Test
    void testUserSearch(){
        User user = new User();
        user.setUserStatus(1);
        PageInfo<User> pageInfo = userService.selectUserByUseridAndUserUsernameAndUserStatus(2, 8, user.getUserId(), user.getUsername(), user.getUserStatus());
        System.out.println(pageInfo);
    }
}
