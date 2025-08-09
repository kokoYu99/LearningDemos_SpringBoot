package com.koko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootApplication //@Configuration + @ComponentScan + @Import
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    /* 配置序列化器 */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        //创建template对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //设置连接工厂。连接工厂会帮我们管理redis连接
        redisTemplate.setConnectionFactory(connectionFactory);

        //设置key的序列化 -> String
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        //创建json序列化工具
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //设置value的序列化 -> json
        redisTemplate.setValueSerializer(jsonRedisSerializer);

        return redisTemplate;
    }

    /* 加载lua文件，使用redis事务 */
    @Bean
    public RedisScript<Boolean> redisScript(){
        ClassPathResource resource = new ClassPathResource("lua/test.lua");
        return RedisScript.of(resource, Boolean.class);
    }

}