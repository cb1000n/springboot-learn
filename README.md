# springboot-learn

## 介绍
springboot 学习

# 新建 | 打开 springboot-learn 空项目

**我的代码是全部上传码云的，所以码云新建空项目，然后拉下来直接用idea打开。**

码云空项目

![image-20210128142837418](README.assets/image-20210128142837418.png)

拉的空项目

![image-20210128142944537](README.assets/image-20210128142944537.png)

`README.assets`是我用`Typora`编写`README.md`文件时生成的存放图片文件的目录。

用`IDEA`打开这个空项目

![image-20210128143201018](README.assets/image-20210128143201018.png)

![image-20210128143226505](README.assets/image-20210128143226505.png)

如图，就是一个空项目。

![image-20210128143247856](README.assets/image-20210128143247856.png)

# SpringBoot 集成 RabbitMQ

## 服务器准备 docker-rabbitmq

```shell
# 一、获取镜像
# 	指定版本，该版本包含了web控制页面
docker pull rabbitmq:management

# 二、运行镜像
# 	方式一：默认guest 用户，密码也是 guest
docker run -d --hostname rabbitmq-learn --name rabbitmq-learn -p 15672:15672 -p 5672:5672 rabbitmq:management
# 	方式二：设置用户名和密码
docker run -d --hostname rabbitmq-learn --name rabbitmq-learn -e RABBITMQ_DEFAULT_USER=user -e  ABBITMQ_DEFAULT_PASS=password -p 15672:15672 -p 5672:5672 rabbitmq:management
# 三、访问ui页面
http://localhost:15672/
```

## 新建`rabbitmq-learn`模块

![image-20210128143451200](README.assets/image-20210128143451200.png)

![image-20210128143516602](README.assets/image-20210128143516602.png)

![image-20210128143633112](README.assets/image-20210128143633112.png)

![image-20210128143707850](README.assets/image-20210128143707850.png)

![image-20210128143729626](README.assets/image-20210128143729626.png)

删除不必要的文件

![image-20210128143829232](README.assets/image-20210128143829232.png)

删除后如图

![image-20210128143852839](README.assets/image-20210128143852839.png)

## 引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

## 配置文件

删除其他，然后新建配置文件：`application.yml`

配置如下

```yml
spring:
  rabbitmq:
    host: 106.54.139.107
    port: 5672
    username: guest
    password: guest
```

## 测试代码如下

新建测试类`src/test/java/com/zhang/test/TestRabbitMQ.java`

内容如下

```java
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
```

响应的消费者类`src/main/java/com/zhang/hello/HelloCustomer.java`

```java
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
```

`src/main/java/com/zhang/work/WorkCustomer.java`

```java
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
```

`src/main/java/com/zhang/fanout/FanoutCustomer.java`

```java
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
```

`src/main/java/com/zhang/route/RouteCustomer.java`

```java
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName RouteCustomer
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/1/28 16:45
 */
@Component
public class RouteCustomer {
    @RabbitListener(bindings = {
            @QueueBinding( // 将队列绑定到交换机
                    value = @Queue, // 不指定名称产生临时队列
                    exchange = @Exchange(value = "directs", type = "direct"), // 绑定的交换机
                    key = {"info", "error", "warn"}
            )
    })
    public void receive1(String message) {
        System.out.println("message1 " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding( // 将队列绑定到交换机
                    value = @Queue, // 不指定名称产生临时队列
                    exchange = @Exchange(value = "directs", type = "direct"), // 绑定的交换机
                    key = {"error"}
            )
    })
    public void receive2(String message) {
        System.out.println("message2 " + message);
    }
}
```

`src/main/java/com/zhang/topic/TopicCustomer.java`

```java
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
```

