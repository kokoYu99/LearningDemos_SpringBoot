package com.koko.service.impl;

import com.koko.exception.BusinessException;
import com.koko.mapper.UserMapper;
import com.koko.pojo.User;
import com.koko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    /* 登录检查用户名和密码 */
    @Override
    public HashMap<String, Long> loginCheck(User user) {
        User userChecked = mapper.loginCheck(user.getUsername(), user.getPassword());

        //校验不通过
        if (userChecked == null) {
            throw new BusinessException(400, "用户名或密码错误");
        }

        //校验通过，返回uid (主键回显)
        HashMap<String, Long> data = new HashMap<>();
        data.put("uid", userChecked.getUId());
        return data;
    }

    /* 查询用户详情 */
    @Override
    public User queryDetails(Long id) {
        User user = mapper.queryDetails(id);
        //将密码置空，再返回json
        //在user实体类中添加注解 @JsonInclude(JsonInclude.Include.NON_NULL)，如果密码的值为空，json中就不包含密码属性
        user.setPassword(null);
        return user;
    }

    /* 修改用户详情(手机号、简介) */
    @Override
    public void update(User user) {
        user.setUpdateTime(new Date());
        int rows = mapper.update(user);

        if (rows == 0) {
            throw new BusinessException("修改失败，请稍后再试");
        }
    }
}
