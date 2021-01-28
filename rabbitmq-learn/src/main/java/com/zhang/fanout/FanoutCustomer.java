package com.zhang.fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName FanoutCustomer
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/1/28 16:39
 */
@Component
public class FanoutCustomer {

    @RabbitListener(bindings = {
            @QueueBinding( // 将队列绑定到交换机
                    value = @Queue, // 不指定名称产生临时队列
                    exchange = @Exchange(value = "logs", type = "fanout") // 绑定的交换机
            )
    })
    public void receive1(String message) {
        System.out.println("message1 " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding( // 将队列绑定到交换机
                    value = @Queue, // 不指定名称产生临时队列
                    exchange = @Exchange(value = "logs", type = "fanout") // 绑定的交换机
            )
    })
    public void receive2(String message) {
        System.out.println("message2 " + message);
    }
}