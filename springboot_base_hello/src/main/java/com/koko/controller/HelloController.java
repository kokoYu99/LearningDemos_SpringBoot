package com.koko.controller;

import com.koko.pojo.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* 步骤4. 在启动类同包下，创建Controller类 */

@RestController
@RequestMapping("boot")
public class HelloController {

    @GetMapping("hello")
    public String sayHello(){
        System.out.println("HelloController.sayHello");
//        return "<h1>hello springboot <br/> 你好 春天靴</h1>";
        return "hello springboot 你好 春天靴";
    }

    @GetMapping("user")
    public User getUser(){
        User user = new User();
        user.setName("koko");
        user.setAge(11);

        return user;
    }

}
