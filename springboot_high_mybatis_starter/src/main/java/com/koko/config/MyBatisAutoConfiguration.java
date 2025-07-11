package com.koko.config;

import com.koko.factory.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/* 模拟MyBatis的配置类 */

@ComponentScan
@Configuration
public class MyBatisAutoConfiguration {

    //装配配置读取类
    @Autowired
    private MyBatisProperties properties;

    //@Bean方法 实例化，返回组件类对象
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        //实例化组件对象
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();

        //从配置读取类中取参数，给组件对象赋值
        sqlSessionFactory.setInfo("properties.getUsername() = " + properties.getUsername() + ", " + "properties.getPassword() = " + properties.getPassword());

        System.out.println("properties.getUsername() = " + properties.getUsername() + ", " + "properties.getPassword() = " + properties.getPassword());

        //返回组件对象
        return sqlSessionFactory;
    }
}
