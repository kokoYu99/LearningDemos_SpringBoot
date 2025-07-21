package com.koko.service;

import com.koko.pojo.User;

import java.util.HashMap;
import java.util.Map;

public interface UserService {
    HashMap<String, Long> loginCheck(User user);

    User queryDetails(Long id);

    void update(User user);
}
