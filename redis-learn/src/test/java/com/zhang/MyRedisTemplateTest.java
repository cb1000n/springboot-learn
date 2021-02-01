package com.zhang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhang.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * ClassName MyRedisTemplateTest
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/2/1 11:39
 */
@SpringBootTest
public class MyRedisTemplateTest {

    @Autowired
    @Qualifier("redisTemplate") // 重名后，设置用户名，引入的是自己配置的 RedisTemplate
    private RedisTemplate redisTemplate;

    @Test
    void testRedisTemplate() {
        redisTemplate.opsForValue().set("test", "123");
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Test
    void testSavePojo() throws JsonProcessingException {
        User user = new User("test", 3);
        // 自定义的RedisTemplate配置了实体类等一系列数据类型的序列化方式
        redisTemplate.opsForValue().set("user", user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

}