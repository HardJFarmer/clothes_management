package com.ccsu.clothesmanagement;

import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClothesManagementApplicationTests {
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

}
