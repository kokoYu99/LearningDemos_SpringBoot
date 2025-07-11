package com.koko.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config02 {
    @Bean
    public BBB bbb(){
        return new BBB();
    }
}
