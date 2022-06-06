package com.ccsu.clothesmanagement.mapper;

import com.ccsu.clothesmanagement.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {


    User login(User user);
}
