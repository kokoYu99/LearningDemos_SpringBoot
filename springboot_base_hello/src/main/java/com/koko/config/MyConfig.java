package com.koko.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MyImports.class}) //批量导入配置类
public class MyConfig {
}
