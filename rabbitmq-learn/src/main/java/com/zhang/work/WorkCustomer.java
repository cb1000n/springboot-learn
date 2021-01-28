package com.zhang.work;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName WorkCustomer
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/1/28 16:33
 */
@Component
public class WorkCustomer {

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receive1 (String message) {
        System.out.println("message1 = " + message);

    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receive2 (String message) {
        System.out.println("message2 = " + message);

    }
}