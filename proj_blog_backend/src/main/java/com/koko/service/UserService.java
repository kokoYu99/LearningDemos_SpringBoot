package com.koko.service;

import com.koko.pojo.User;

public interface UserService {
    void loginCheck(User user);

    User queryDetails(Long id);

    void update(User user);
}
