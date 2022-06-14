package com.ccsu.clothesmanagement.service.impl;

import com.ccsu.clothesmanagement.domain.User;
import com.ccsu.clothesmanagement.mapper.UserMapper;
import com.ccsu.clothesmanagement.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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


    /**
     * 查询所有user信息
     * @return user集合
     */
    @Override
    public PageInfo<User> getUserList(int pageNum, int pageSize) {
        if (pageSize == 0){
            pageSize = 8;
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.getUserList(), 5);
        return pageInfo;
    }

    @Override
    public int addUser(String username,String password) {
        int row=userMapper.addUser(username, "123456");
        return row;
    }

    @Override
    public int updateUserById(User user){
        int row=userMapper.updateUserById(user);
        return row;
    }

    @Override
    public int deleteUser(Integer userid) {
        int row=userMapper.deleteUser(userid);
        return row;
    }

    @Override
    public PageInfo<User> selectUserById(int pageNum, int pageSize, Integer userId){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectUserById(userId));
        return pageInfo;
    }

    @Override
    public PageInfo<User> selectUserByUseridAndUserUsernameAndUserStatus(int pageNum, int pageSize,Integer userid,String username, Integer userStatus) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectUserByUseridAndUserNameAndUserStatus(userid,username,userStatus));
        return pageInfo;
    }

}
