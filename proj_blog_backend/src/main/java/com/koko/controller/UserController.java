package com.koko.controller;

import com.koko.pojo.User;
import com.koko.service.UserService;
import com.koko.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/login")
    public Result<Object> loginCheck(@RequestBody User user) {
        service.loginCheck(user);
        return Result.ok();
    }

    @GetMapping("{id}")
    public Result<User> queryDetails(@PathVariable Long id) {
        User user = service.queryDetails(id);
        return Result.ok(user);
    }

    @PostMapping
    public Result<Object> updateUser(@Validated @RequestBody User user){
        service.update(user);
        return Result.ok();
    }

}
