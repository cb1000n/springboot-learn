package com.zhang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisLearnApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;


    /*
     redisTemplate    操作不同的数据类型，qpi 和我们的指令是一样的
     opsForXX         操作XX类型的数据
     opsForValue      操作字符串   类似String
     opsForList
     opsForSet
     opsForHash
     opsForZSet
     opsForGeo（地图）
     opsForHyperLogLog
     */

    @Test
    void testRedisTemplate() {
        redisTemplate.opsForValue().set("test", "123");
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Test
    void testSavePojo() throws JsonProcessingException {
        User user = new User("test", 3);
        // Jackson 序列化对象为Json
        String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user", jsonUser);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
}
