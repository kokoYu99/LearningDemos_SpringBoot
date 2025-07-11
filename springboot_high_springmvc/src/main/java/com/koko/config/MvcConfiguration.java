package com.koko.config;

import com.koko.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor) //添加拦截器
                .addPathPatterns("/**") //要拦截的路径
                .excludePathPatterns("/user/aa") //要排除的路径
                .order(10); //路径优先级
    }
}
