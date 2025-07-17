package com.koko.mapper;

import com.koko.pojo.User;

public interface UserMapper {
    int loginCheck(String username, String password);

    User queryDetails(Long id);

    int update(User user);
}
