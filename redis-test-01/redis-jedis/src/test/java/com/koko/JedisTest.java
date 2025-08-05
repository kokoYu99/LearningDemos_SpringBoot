package com.koko;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * TODO：
 *  Jedis使用步骤
 * 1. 创建jedis连接对象，构造器：new Jedis(String host, int port)
 * 2. 使用连接对象
 * 3. 关闭资源
 * */
public class JedisTest {
    //创建全局jedis对象
    private Jedis jedis;

    /* 连通测试 */
    @Test
    public void test() {
        String ping = jedis.ping();
        System.out.println("ping = " + ping);
    }

    /**
     * 使用jedis对象，调用与指令同名的方法，来操作redis
     */

    /* 设置、获取 key-value */
    @Test
    public void testKey() {
        //设置key-value
        jedis.set("key", "value");

        //设置key-value，并设置过期时间(秒)
        jedis.setex("key1", 60, "value");

        //查看指定key还有多久秒过期
        System.out.println(jedis.ttl("key1"));

        //删除指定key，删除成功返回1
        System.out.println(jedis.del("key1"));

        //获取当前库的所有key
        Set<String> keys = jedis.keys("*");
        keys.forEach(System.out::println);

        //获取当前库所有key的数量
        System.out.println(jedis.dbSize());

        //判断某个key是否存在
        System.out.println(jedis.exists("key"));

        //获取指定key的类型
        System.out.println(jedis.type("key"));

        //清空当前库
        System.out.println(jedis.flushDB());

    }

    /* 设置、获取String类型的key-value */
    @Test
    public void testString() {
        //设置一对key-value
        jedis.set("k1", "v1");

        //设置多对
        jedis.mset("k01", "1", "k02", "2");

        //根据key，在其value后追加新值
        jedis.append("k01", "111111111");

        //当key不存在时，设置key-value的值
        jedis.setnx("k01", "vvv");
        jedis.setnx("k03", "vvv");

        //获取多对
        System.out.println(jedis.mget("k01", "k02", "k03"));

        //获取value的长度
        System.out.println(jedis.strlen("k01"));

        //增加/减少value的值(仅限数值类型的value)
        System.out.println(jedis.incr("k01"));
        System.out.println(jedis.incrBy("k01", 100));

    }

    /* 添加、获取List列表的元素 */
    @Test
    public void testList() {
        jedis.flushDB();

        //向List放入元素
        System.out.println(jedis.lpush("list", "a", "b", "c", "d"));

        //获取List中所有元素
        System.out.println(jedis.lrange("list", 0, -1));

        //从右边吐出一个值
        System.out.println(jedis.rpop("list"));
    }

    /* Set 无序去重集合 */
    @Test
    public void testSet() {
        //添加一个set集合
        jedis.sadd("set", "1", "2", "3", "4", "5", "6");

        //获取set和所有元素
        Set<String> set = jedis.smembers("set");
        set.forEach(System.out::println);

        //判断是否包含此元素
        System.out.println(jedis.sismember("set", "2"));

        //删除指定元素
        System.out.println(jedis.srem("set", "4"));

        //随机吐出一个元素
        System.out.println(jedis.spop("set"));
        System.out.println(jedis.spop("set", 2));

        //随机取出一个元素，但不会删除
        System.out.println(jedis.srandmember("set"));

        //将元素从一个set转移到另一set
        System.out.println(jedis.smove("set", "newSet", "6"));

    }

    /* 添加、获取有序集合ZSet的元素 */
    @Test
    public void testZSet() {
        jedis.flushDB();

        Map<String, Double> map = new HashMap<>();
        map.put("koko", 100d);
        map.put("jojo", 100d);
        map.put("momo", 90d);

        //添加元素及其score到有序集合scores中
        jedis.zadd("scores", map);

        //升序返回全部元素member(index在0和-1之间)
        Set<String> scores = jedis.zrange("scores", 0, -1);
        System.out.println("scores = " + scores);

        //降序返回全部元素
        System.out.println(jedis.zrevrange("scores", 0, -1));

        //降序返回 值在0-100之间的元素，并显示其score。从index为0的元素开始，取2个元素
        Set<Tuple> scos = jedis.zrevrangeByScoreWithScores("scores", 100d, 0d, 0, 2);
        for (Tuple tuple : scos) {
            String member = new String(tuple.getBinaryElement());
            double score = tuple.getScore();
            System.out.println("member = " + member + ", score = " + score);
        }

        //给指定元素增加score
        System.out.println(jedis.zincrby("scores", 5, "momo"));

        //删除元素
        System.out.println(jedis.zrem("scores", "jojo"));

        //返回score在指定区间内的元素个数
        System.out.println(jedis.zcount("scores", 90, 100));

        //返回指定元素在集合中的正序排名
        System.out.println(jedis.zrank("scores", "koko"));
    }

    /* Hash 存储对象 */
    @Test
    public void testHash() {
        //设置key-field-value
        jedis.hset("user1", "name", "koko");
        jedis.hset("user1", "age", "11");
        jedis.hset("user1", "gender", "female");

        //批量设置值
        HashMap<String, String> user2 = new HashMap<>();
        user2.put("name", "momo");
        user2.put("age", "2");
        user2.put("gender", "female");
        jedis.hmset("user2", user2);

        //获取值
        System.out.println(jedis.hget("user1", "name"));
        System.out.println(jedis.hget("user2","name"));

        //查看field是否存在
        System.out.println(jedis.hexists("user1", "age"));

        //查看一个hash集合中所有field
        System.out.println(jedis.hkeys("user1"));

        //查看一个hash集合中所有value
        System.out.println(jedis.hvals("user1"));

        //给制定field的value增加值
        System.out.println(jedis.hincrBy("user1", "age", 10));

        //如果field不存在，则添加值
        System.out.println(jedis.hsetnx("user1", "height", "211"));
        System.out.println(jedis.hget("user1", "height"));

    }

    //创建redis对象
    @BeforeEach
    public void before() {
        jedis = new Jedis("192.168.6.100", 6379);
    }

    //关闭redis对象资源
    @AfterEach
    public void after() {
        jedis.close();
    }
}
