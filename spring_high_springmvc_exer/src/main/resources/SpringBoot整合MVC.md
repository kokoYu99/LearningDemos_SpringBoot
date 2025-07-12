# 1. 创建mvc基础项目
## 1.1 导入依赖
父工程：spring-boot-starter-parent

依赖：
spring-boot-starter-web(包含jackson，具备json处理能力)，
lombok


## 1.2 创建启动类@SpringBootApplication
```java
/* 创建启动类 */

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```


## 1.3 创建pojo实体类
```java
@Data
public class User {
    private String username;
    private String password;
    private Integer age;
    private String gender;
}

```


## 1.4 创建controller类
```java
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping
    public User getUser() {
        User user = new User();

        user.setUsername("koko");
        user.setPassword("123456");
        user.setGender("女");
        user.setAge(22);

        return user;
    }
}
```


## 1.5 启动项目，访问测试
用get请求访问 http://localhost:8888/user ，测试是否能正常访问



# 2. web场景启动器的配置
application.yaml
端口号、项目根路径
```yaml
server:
  port: 8888 #端口号
  servlet:
    context-path: / #项目根路径(上下文路径)
```



# 3. 静态资源存储路径
SSM：需要 `<mvc:default-servlet-handler/>`
SpringBoot：约定>配置。静态资源放在固定路径，即可找到

默认路径：
```
classpath:/static/ (推荐)
classpath:/public/
classpath:/resources/
classpath:META-INF/resources/
```

修改默认路径：
```yaml
spring:
  web:
    resources:
      static-locations: classpath:/static/ #静态资源的存放路径
```



# 4. 拦截器的配置
## 4.0 SSM方式回顾
1. 定义拦截器类
2. 在xml中配置拦截器

```xml

<mvc:interceptors>
    <mvc:interceptor>
        <!--设置拦截路径-->
        <mvc:mapping path=""/>
        <!-- 排除拦截路径 -->
        <mvc:exclude-mapping path=""/>
        <!-- 指定拦截器 -->
        <bean class=""/>
    </mvc:interceptor>
    <!-- 根据多个拦截器的声明顺序，决定优先级Order -->
</mvc:interceptors>
```

## 4.1 定义拦截器
```java
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor.preHandle");
        return true;
    }
}
```


## 4.2 定义MVC标准配置类，配置拦截器
```java
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
```




