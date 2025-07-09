package com.koko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("boot")
public class HelloHelloController {

    /* 批量注入前的写法： */
/*    @Value("${my.data.name}")
    private String name;
    @Value("${my.data.account.username}")
    private String username;*/

    /* 批量注入，获取配置读取类组件 */
    @Autowired
    private MyDataConfigProps configProps;

    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("HelloHelloController.sayHello");
        return "hello, " + configProps.getName()
                + "<br/> 你的用户名是 " + configProps.getAccount().getUsername()
                + "<br/> 你的密码是 " + configProps.getAccount().getPassword();
    }
}
