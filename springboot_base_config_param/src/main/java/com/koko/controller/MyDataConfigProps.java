package com.koko.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
* 批量读取配置文件中的属性
* 步骤
* 1. 创建一个配置读取类组件
* 2. 类上添加注解@ConfigurationProperties
* 3. 注解的属性prefix设为属性前缀(第1到n-1层)
* 4. 在类中定义属性，必须与属性的最后一层key一致
* */

@Data
@ConfigurationProperties(prefix = "my.data")
@Component
public class MyDataConfigProps {

    private String name;

    private Account account;

    //使用内部类来映射对象类型的属性
    //也可以拆分为name和account两个配置读取类
    @Data
    public static class Account{
        private String username;
        private String password;
    }

}
