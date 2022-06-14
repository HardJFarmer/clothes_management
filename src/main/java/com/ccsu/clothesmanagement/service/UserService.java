package com.ccsu.clothesmanagement.service;

import com.ccsu.clothesmanagement.domain.User;
import com.github.pagehelper.PageInfo;

public interface UserService {

    User login(User user);

    PageInfo<User> getUserList(int pageNum, int pageSize);

    int addUser(String username,String password);

    int updateUserById(User user);

    int deleteUser(Integer userid);

    PageInfo<User> selectUserById(int pageNum, int pageSize,Integer userId);

    PageInfo<User> selectUserByUseridAndUserUsernameAndUserStatus(int pageNum, int pageSize,Integer userid,String username,Integer userStatus);
}
