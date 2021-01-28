package com.zhang.test;

import com.zhang.RabbitmqLearnApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName TestRabbitMQ
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/1/28 14:49
 */
@SpringBootTest(classes = RabbitmqLearnApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // hello world
    @Test
    public void testHello() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }

    // work 默认轮询
    @Test
    public void testWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "work 模型"+i);
        }
    }

    // fanout 广播
    @Test
    public void testFanout() {
        rabbitTemplate.convertAndSend("logs", "", "Fanout 模型发送的消息");
    }

    // route 广播
    @Test
    public void testRoute() {
        rabbitTemplate.convertAndSend("directs", "info", "Route 模型发送的消息");
    }

    // topic 广播
    @Test
    public void testTopic() {
        rabbitTemplate.convertAndSend("topics", "user.save", "user.save 路由消息");
    }
}