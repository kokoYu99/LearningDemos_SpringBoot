package com.koko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class DataSourceController {

    @Autowired
    private DataSourceConfigProps configProps;

    @GetMapping("info")
    public String getInfo() {
        return configProps.getUrl() + "<br>"
                + configProps.getDriverClassName()  + "<br>"
                + configProps.getUsername()  + "<br>"
                + configProps.getPassword();
    }
}
