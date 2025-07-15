# 1. 导入依赖
```xml
<!-- 项目打包插件：将SpringBoot项目打包为可执行的jar包 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <version>3.5.3</version>
</dependency>
```

# 2. 清理，打包项目
```
mvn clean package
```

# 3. 查看jar包
图

# 4. 运行jar包
将jar包存到本地，打开cmd窗口，执行指令：
```dos
java -jar 项目名称
```
动态选择运行的环境配置profile，如dev环境：
```dos
java -jar -Dspring:profiles:active:dev 项目名称
```