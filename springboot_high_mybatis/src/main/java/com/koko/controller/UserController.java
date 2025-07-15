package com.koko.controller;

import com.koko.mapper.UserMapper;
import com.koko.pojo.User;
import com.koko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getDetailsById(@PathVariable Integer id) {
        User user = userService.getDetailsById(id);
        return user;
    }
}
