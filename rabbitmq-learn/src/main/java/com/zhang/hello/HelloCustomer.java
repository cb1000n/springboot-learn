package com.zhang.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName HelloCustomer
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/1/28 14:53
 */
@Component
/*
@RabbitListener(queuesToDeclare = @Queue(value = "hello", durable = "false", autoDelete = "true"))

@RabbitListener 队列监听者 | 消费者
queuesToDeclare 配置队列信息，如果不存在则新建
value 队列名
durable 是否持久化
autoDelete 是否自动删除

默认创建的就是 持久化、非独占、不自动删除的队列
 */
@RabbitListener(queuesToDeclare = @Queue(value = "hello"))
public class HelloCustomer {

    @RabbitHandler
    public void receive1(String message) {
        System.out.println("---------------------hello---------------------");
        System.out.println("message = " + message);
    }
}