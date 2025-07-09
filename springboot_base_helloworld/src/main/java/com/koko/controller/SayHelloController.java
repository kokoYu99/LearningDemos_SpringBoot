package com.koko.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//步骤4. 在启动类的同一包下，定义Controller类(和SpringMVC一样)

@RestController
@RequestMapping("boot")
public class SayHelloController {

    @GetMapping("hello")
    public String sayHello(){
        System.out.println("SayHelloController.sayHello");
        return "hello springboot!!!";
    }
}
