# 1. 整合Tx声明式事务
## 1.1 导入场景启动器
```xml
<!-- 事务场景启动器 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```


## 1.2 直接使用注解@Transactional
SSM方式： [[]]
1. 创建事务管理器TransactionManager，配置DataSource；
2. 开启事务注解支持

SpringBoot方式：直接使用注解即可

```java
@Transactional(
        readOnly = true,
        timeout = 3,
        rollbackFor = Exception.class,
        isolation = Isolation.READ_COMMITTED)
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getDetailsById(Integer id) {
        User user = userMapper.queryById(id);
        return user;
    }
}
```



# 2. 整合AOP
## 2.1 导入场景启动器
```xml
        <!-- AOP场景启动器 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```


## 2.2 自定义切面类
定义方式同SSM [[]]
```java
@Aspect
@Component
public class LogAspect {

    //提取切点表达式
    @Pointcut("execution(* com..service.*.*(..))")
    private void pointcut() {

    }

    /* 编写不同位置的增强方法，并选中切点 */

    @Before(value = "pointcut()")
    public void before() {
        System.out.println("闪亮登场!!!");
    }

    @After(value="pointcut()")
    public void after(){
        System.out.println("遗憾离场...");
    }
}
```