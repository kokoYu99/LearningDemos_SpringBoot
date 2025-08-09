# 1. 准备工作

## 1.1 创建SpringBoot工程

![img.png](img.png)


## 1.2 导入依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!-- 指定父工程 -->
    <parent>
        <artifactId>spring-boot-starter-parent</artifactId>
        <groupId>org.springframework.boot</groupId>
        <version>3.0.5</version>
    </parent>

    <groupId>com.koko</groupId>
    <artifactId>redis-RedisTemplate</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- springboot的核心启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <!-- 进行单元测试和集成测试 的标准工具集。
        它集成了：
        JUnit 5：一个流行的 Java 测试框架。
        Mockito：一个强大的 Mocking 框架，用于创建模拟对象，以便在测试中隔离依赖项。
        Spring Test：提供对 Spring 应用上下文的测试支持。
        AssertJ：一个流畅的断言库，让测试代码更具可读性。
        -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <!-- 用于Java与redis交互 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <!-- Redis的客户端：像 Jedis 或 Lettuce 这样的客户端，在实现连接池时，底层使用了 commons-pool2 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>

        <!-- 本项目中用于设置序列化器 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>
    </dependencies>


    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

</project>
```


## 1.3 编写配置类

```yaml
spring:
  data:
    redis:
      # 主机号
      host: 192.168.6.100
      # 端口号
      port: 6379
      # 配置客户端类型。默认是lettuce
      client-type: lettuce
      # 设置lettuce的底层参数
      lettuce:
        pool:
          # 1. 启用Lettuce连接池
          enabled: true
          # 2. 连接池中允许的最大活动连接数
          max-active: 8
          # 3. 连接池中允许的最大空闲连接数
          max-idle: 5
          # 4. 获取连接时的最大等待时间(ms)
          max-wait: 100



#客户端类型切换为jedis
#spring:
#  data:
#    redis:
#      client-type: jedis
#      jedis:
#        pool:
#          enabled: true
#          max-active: 8

```



# 2. 创建启动类
```java
//启动类，本质是一个配置类
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```



# 3. 配置序列化器
注意：需要先导入jackson-databind的依赖！
或者使用spring-boot-starter-web启动器，其中包含此依赖

```java
//启动类
@SpringBootApplication
public class Main {
    //主方法见上
    
    /**
     * 序列化器的作用：
     * 指定redisTemplate在进行数据处理的时候对应的序列化方式！
     * ------------
     * 序列化器分为3种：
     * jdk  [默认]
     * string
     * json
     * ---------
     * 配置的位置：
     * key
     * value
     * hashKey
     * hashValue
     * ---------
     * 对应配置：
     * key | hashKey -> String序列化器
     * value | hashValue -> Json序列化器
     * ------------------------------------
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // 创建RedisTemplate对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置连接工厂
        template.setConnectionFactory(connectionFactory);

        // 创建JSON序列化工具
        GenericJackson2JsonRedisSerializer jsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();

        // 设置Key的序列化
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        // 设置Value的序列化
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);

        // 返回修改的模板对象
        return template;
    }
}
```



# 4. 编写测试方法
```java
//此注解会测试启动类，创建ioc容器
@SpringBootTest(classes = Main.class)
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String,String> template; //报红线可忽略

    @Test
    public void testString(){
        
        template.opsForValue().set("name","koko");
        System.out.println(template.opsForValue().get("name"));

        template.opsForValue().set("chiName","抠抠余");
        System.out.println(template.opsForValue().get("chiName"));
    }
}

```


在Redis-cli中获取value
因为配置了序列化器，因此获取的value在转换后不会加上多余的前缀
```bash
#如果要获取中文value，连接服务时需要 --raw 
[root@centos ~]# redis-cli -p 6379 --raw
127.0.0.1:6379> get chiName
抠抠余
127.0.0.1:6379> get name
koko
127.0.0.1:6379> 
```













