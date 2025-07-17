package com.koko.service.impl;

import com.koko.exception.BusinessException;
import com.koko.mapper.UserMapper;
import com.koko.pojo.User;
import com.koko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    /* 登录检查用户名和密码 */
    @Override
    public void loginCheck(User user) {
        int rows = mapper.loginCheck(user.getUsername(), user.getPassword());
        if (rows == 0) {
            throw new BusinessException(400, "用户名或密码错误");
        }
    }

    /* 查询用户详情 */
    @Override
    public User queryDetails(Long id) {
        User user = mapper.queryDetails(id);
        user.setPassword("null");
        return user;
    }

    /* 修改用户详情 */
    @Override
    public void update(User user) {
        user.setUpdateTime(new Date());
        int rows = mapper.update(user);

        if (rows == 0) {
            throw new BusinessException("修改失败，请稍后再试");
        }
    }
}
