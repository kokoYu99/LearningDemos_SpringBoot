package com.koko.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix="spring.jdbc.datasource")
@Component
public class DataSourceConfigProps {
    private String url;
    private String driverClassName;
    private String username;
    private String password;
}
