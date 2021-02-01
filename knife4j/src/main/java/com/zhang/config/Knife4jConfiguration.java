package com.zhang.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * ClassName Knife4jConfiguration
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/1/28 20:46
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfiguration {
    // 配置swagger的“我要瘦成一道闪电”分组的Docket实例
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 配置分组名称
                .groupName("我要瘦成一道闪电")
                /*
                配置是否启用swagger，默认true
                启用
                .enable(true)
                不启用
                .enable(false)
                */
                .select()
                /*
                可以不配置，不配置时，扫描全部。
                配置扫描包：
                必须在 .select()和 .build()之间配置
                一、配置扫描路径
                    1. 配置指定扫描的包——最常用
                    .apis(RequestHandlerSelectors.basePackage("com.test.sprinbootswagger2.controller"))
                    2. 扫描全部
                    .apis(RequestHandlerSelectors.any())
                    3. 都不扫描
                    .apis(RequestHandlerSelectors.none())
                    4. 扫描类上有XX注解的类（以@RestController注解为例）
                    .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                    5. 扫描方法上有XX注解的类（以@GetMapping注解为例）
                    .apis(RequestHandlerSelectors.withClassAnnotation(GetMapping.class))
                二、配置过滤路径，优先级比上边的高
                    1. 不扫描某路径
                    .paths(PathSelectors.ant("/test/**"))
                    2. 其他类似上边any、none、regex等
                */
                //
                .apis(RequestHandlerSelectors.basePackage("com.test.sprinbootswagger2.controller"))
                .build();
    }

    // 配置swagger的Docket实例
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2)
                // 配置分组名称
                .groupName("A");
    }
    // 配置swagger的Docket实例
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2)
                // 配置分组名称
                .groupName("B");
    }

    // 配置Swagger“我想做阿信”分组的信息=apiInfo
    private ApiInfo apiInfo() {
        Contact contact = new Contact("我想做阿信","https://blog.csdn.net/qq_42909053/","1273206268@qq.com");
        // 作者信息
        return new ApiInfo(
                "Spring Boot 整合 Knife4j 接口文档",
                "# Spring Boot 整合 Knife4j 接口文档 APIs",
                "v1.0",
                "https://blog.csdn.net/qq_42909053/article/details/104982904",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }

}