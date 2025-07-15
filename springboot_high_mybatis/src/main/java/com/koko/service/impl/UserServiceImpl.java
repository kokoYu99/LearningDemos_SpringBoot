package com.koko.service.impl;

import com.koko.mapper.UserMapper;
import com.koko.pojo.User;
import com.koko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(
        readOnly = true,
        timeout = 3,
        rollbackFor = Exception.class,
        isolation = Isolation.READ_COMMITTED)
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getDetailsById(Integer id) {
        User user = userMapper.queryById(id);
        return user;
    }
}
