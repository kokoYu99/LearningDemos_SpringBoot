package com.koko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* 步骤3. 创建启动类 */

@SpringBootApplication //指定此为SpringBoot的启动类
public class Main {
    public static void main(String[] args) {

        /* 步骤5. 启动程序 */

        //1.创建IoC容器; 2.启动tomcat
        SpringApplication.run(Main.class, args);

    }
}
