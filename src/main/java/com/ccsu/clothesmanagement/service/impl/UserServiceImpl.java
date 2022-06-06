package com.ccsu.clothesmanagement.service.impl;

import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.mapper.UserMapper;
import com.ccsu.clothesmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }
}
