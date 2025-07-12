package com.koko.controller;

import com.koko.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping
    public User getUser() {
        User user = new User();

        user.setUsername("koko");
        user.setPassword("123456");
        user.setGender("女");
        user.setAge(22);

        return user;
    }
}
