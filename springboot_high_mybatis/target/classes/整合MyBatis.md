# 1. 导入场景启动器和依赖
创建springboot工程，指定父工程，导入启动器和依赖
```xml
<dependencies>
    <!-- MyBatis需要的依赖：
    starter-web
    mybatis-spring
    mysql-connector
    lombok
    数据库连接池用springboot自带的，不用导druid -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>3.0.3</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.30</version>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>

</dependencies>
```



# 2. 创建启动类
```java
/* 创建启动类 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```



# 3. 创建实体类pojo
```java
/* 创建实体类 */

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
}
```



# 4. 创建mapper接口和mapperXML文件
```java
//@Mapper //如果已经按包扫描@MapperScan，就不用此注解
public interface UserMapper {
    User queryById(Integer id);
}
```
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.koko.mapper.UserMapper">
    <select id="queryById" resultType="user">
        SELECT * FROM user WHERE id=#{id}
    </select>
</mapper>
```



# 5. 在启动类指定mapper接口@MapperScan
指定mapper接口的方式：

方式1. 在启动类上加 `@MapperScan`
```java
@MapperScan({"com.koko.mapper"}) //等效于ssm的MapperScanner(basePackage)
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```

方式2. 在每个mapper接口上加 `@Mapper`

注意：如果你使用了`@MapperScan`，通常可以不用在每个Mapper接口上加`@Mapper`，但是加了也不会冲突。



# 6. MyBatis配置
```yaml
# MyBatis的配置：
spring:
  #(必需)数据库连接信息
  datasource:
    url: jdbc:mysql://localhost:3306/mybatis-example
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
    type: com.zaxxer.hikari.HikariDataSource

#(可选)指定mapperXML位置、resultType别名、驼峰映射、自动映射resultMap、日志实现
mybatis:
  mapper-locations: classpath:mapper/*Mapper.xml
  type-aliases-package: com.koko.pojo
  configuration:
    map-underscore-to-camel-case: true
    auto-mapping-behavior: full
    log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl
```




