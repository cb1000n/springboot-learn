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

```

