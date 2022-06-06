package com.ccsu.clothesmanagement.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private Integer userId;
    private String username;
    private String password;
    private Integer userStatus;
    private Integer isAdmin;
}
