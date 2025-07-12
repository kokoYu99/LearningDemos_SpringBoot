package com.koko.config;

import com.koko.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/* 定义一个SpringMVC的标准配置类，
实现WebMvcConfigurer接口，
此接口提供了各个方向的配置方法可供重写 */

@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    //重写添加拦截器的方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //向registry添加参数。可以链式调用
        registry.addInterceptor(myInterceptor) //添加拦截器
                .addPathPatterns("/**") //添加拦截路径
                .excludePathPatterns("/user/**") //排除拦截路径
                .order(10); //设置拦截优先级
    }
}
