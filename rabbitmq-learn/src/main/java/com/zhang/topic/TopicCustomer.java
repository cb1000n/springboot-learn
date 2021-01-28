package com.zhang.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName TopicCustomer
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/1/28 16:50
 */
@Component
public class TopicCustomer {

    /*
    * 代表一个任意单词
    # 代表任意个数、任意单词
     */

    @RabbitListener(bindings = {
            @QueueBinding( // 将队列绑定到交换机
                    value = @Queue, // 不指定名称产生临时队列
                    exchange = @Exchange(type = "direct", name = "topics"), // 绑定的交换机
                    key = {"user.save", "user.*"}
            )
    })
    public void receive1(String message) {
        System.out.println("message1 " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding( // 将队列绑定到交换机
                    value = @Queue, // 不指定名称产生临时队列
                    exchange = @Exchange(type = "direct", name = "topics"), // 绑定的交换机
                    key = {"order.#", "produce.#", "user.*"}
            )
    })
    public void receive2(String message) {
        System.out.println("message2 " + message);
    }
}