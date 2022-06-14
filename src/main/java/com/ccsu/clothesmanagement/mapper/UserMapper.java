package com.ccsu.clothesmanagement.mapper;

import com.ccsu.clothesmanagement.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 查询出所有用户
     * @return
     */
    List<User> getUserList();

    /**
     * 增加新用户
     */
    int addUser(@Param("username") String username,@Param("password") String password);

    /**
     * 修改用户信息
     */
    int updateUserById(User user);

    /**
     * 删除用户
     */
    int deleteUser(@Param("userId") Integer userid);

    /**
     * 多条件查询用户
     */
    List<User> selectUserByUseridAndUserNameAndUserStatus(@Param("userid") Integer userid,@Param("username") String username,@Param("userStatus") Integer userStatus);

    /**
     * id号查询用户
     */
    List<User> selectUserById(@Param("userId") Integer userId);
}
