package com.koko.controller;

import com.koko.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUser")
    @ResponseBody
    public User getUser(){

        User user = new User();
        user.setUsername("姐");
        user.setPassword("123456");
        user.setAge(18);
        user.setGender("女");

        return user;
    }
}