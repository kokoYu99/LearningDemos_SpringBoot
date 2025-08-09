package com.koko.test;

import com.koko.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = Main.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String,Object> template;

    @Test
    public void test(){
        //设置值
        template.opsForHash().put("food", "snack","cake");
        //取值
        Object o = template.opsForHash().get("food", "snack");
        //输出
        System.out.println("o = " + o);
    }

    @Autowired
    private RedisScript<Boolean> script;

    @Test
    public void testScript(){
        //设置key-value
        template.opsForValue().set("user", "root");

        //创建集合，放入key。单元素集合，Collections.singletonList()
        List<String> list = Collections.singletonList("user");

        //执行lua脚本的命令：如果user的值是root，就改为admin，返回true；不是，就返回false
        //并输出执行结果
        /**
         * redisTemplate的execute(脚本对象, 脚本中的参数1, 参数2...)方法
         * 第一个参数 RedisScript script：Lua脚本
         * 第二个参数 List keys：集合
         *   - 如果是单个参数，使用这个可以转换为单元素集合
         *     - Collections.singletonList(参数)；
         *   - 多参数
         *     - `List<String> keys = Arrays.asList(key1, key2, key3);`
         * 第三个参数 args：**ARGV，也就是其他类型参数
         */
        System.out.println(template.execute(script, list, "root", "admin")); //true

        //获取当前的value值
        System.out.println(template.opsForValue().get("user")); //admin
    }
}
