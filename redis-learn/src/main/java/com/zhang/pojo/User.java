package com.zhang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * ClassName User
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/2/1 11:08
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private String name;
    private int age;
}