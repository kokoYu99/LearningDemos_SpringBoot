package com.koko.test;

import com.koko.Main;
import io.lettuce.core.ReadFrom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

//此注解会测试启动类，创建ioc容器
@SpringBootTest(classes = Main.class)
public class RedisTemplateTest {

    //RedisTemplate，用于执行redis命令
    @Autowired
    private RedisTemplate<String, String> template; //报红线可忽略

    @Test
    public void testString() {
        template.opsForValue().set("name", "koko");
        System.out.println(template.opsForValue().get("name"));

        template.opsForValue().set("chiName", "抠抠余");
        System.out.println(template.opsForValue().get("chiName"));
    }


    //Lua脚本，自动装配
    @Autowired
    private RedisScript<Boolean> script;

    @Test
    public void testScript() {
        //设置Key-value
        template.opsForValue().set("key", "root");

        //创建单元素集合，放入key
        List<String> list = Collections.singletonList("key");

        /**
         * redisTemplate的execute()方法
         * 第一个参数 RedisScript script：Lua脚本
         * 第二个参数 List keys：集合
         *   - 如果是单个参数，使用这个可以转换为单元素集合
         *     - Collections.singletonList(参数)；
         *   - 多参数
         *     - `List<String> keys = Arrays.asList(key1, key2, key3);`
         * 第三个参数 args：**ARGV，也就是其他类型参数
         */
        Boolean execute = template.execute(script, list, "root", "leaf");
        //打印执行结果
        System.out.println("execute = " + execute);

        //获取修改后的value值
        System.out.println("key = " + template.opsForValue().get("key"));
    }



}
