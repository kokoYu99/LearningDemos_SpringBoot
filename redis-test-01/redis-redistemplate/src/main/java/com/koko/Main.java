package com.koko;

import io.lettuce.core.ReadFrom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

//启动类，本质是一个配置类
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    //注意：需要先导入jackson-databind的依赖！
    //或者使用spring-boot-starter-web启动器，其中包含此依赖

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
     * @return RedisTemplate
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


    /* 加载Lua脚本 */
    @Bean
    public RedisScript<Boolean> script() {
        ClassPathResource resource = new ClassPathResource("lua/test.lua");
        return RedisScript.of(resource, Boolean.class);
    }


    /**
     * 配置主从节点(主机从机)访问策略
     * 枚举值：
     * MASTER：从主节点读取
     * MASTER_PREFERRED：优先从master节点读取，master不可用才读取replica
     * REPLICA：从slave(replica)节点读取
     * REPLICA_PREFERRED：优先从slave(replica)节点读取，所有slave都不可用才读取master
     * -----------------------------
     * @return LettuceClientConfigurationBuilderCustomizer 用于自定义Lettuce客户端的配置
     * 当Spring Boot自动配置Redis时，会自动调用这个方法，并使用它的返回值来修改默认配置。
     */
    @Bean
    public LettuceClientConfigurationBuilderCustomizer clientConfigurationBuilderCustomizer() {

        // clientConfigurationBuilder是传入的配置构建器对象。
        return clientConfigurationBuilder -> {

            // readFrom()方法设置了Redis的读操作访问策略，即数据应该从哪个节点读取。
            clientConfigurationBuilder.readFrom(
                    ReadFrom.REPLICA_PREFERRED
                    // ReadFrom.REPLICA_PREFERRED 优先从slave(replica)节点读取，所有slave都不可用才读取master
                    // 这种策略常用于读多写少的场景，以分散读负载，减轻主节点的压力。
            );
        };


    }

}