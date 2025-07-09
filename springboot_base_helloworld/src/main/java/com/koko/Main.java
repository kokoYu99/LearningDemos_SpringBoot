package com.koko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//步骤3. 编写启动类

@SpringBootApplication //SpringBoot启动类
public class Main {
    public static void main(String[] args) {
        System.out.println("Main.main");

        //步骤5. 启动程序
        //1.创建ioc容器 2.启动Tomcat
        SpringApplication.run(Main.class, args);
    }
}
