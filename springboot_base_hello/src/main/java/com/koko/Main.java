package com.koko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/* 步骤3. 创建启动类 */

@SpringBootApplication //指定此为SpringBoot的启动类
public class Main {
    public static void main(String[] args) {

        /* 步骤5. 启动程序 */

        //1.创建IoC容器; 2.启动tomcat
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);

        //默认加载的组件数量
        System.out.println("applicationContext.getBeanDefinitionCount() = " + applicationContext.getBeanDefinitionCount());

        //所有加载的组件的名称
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(System.out::println);

    }
}
