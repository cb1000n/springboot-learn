package com.zhang.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * ClassName SecurityConfig
 * Description TODO 类描述
 *
 * @author ZhangRenjie
 * Date  2021/2/1 16:30
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置首页所有人可访问，功能也只有对应权限的人才能访问
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        // 防止网站攻击 csrf
        http.csrf().disable();
        // 配置没有权限跳转登录页
        http.formLogin();
        // 开启注销，并跳到昼夜
        http.logout().logoutSuccessUrl("/");

        // 开启记住我，自定义接收参数
        http.rememberMe().rememberMeParameter("remember");

        // 定制登录页
        http.formLogin().loginPage("/toLogin").usernameParameter("user").passwordParameter("pwd").loginProcessingUrl("/login");
    }

    /*
    认证
    springboot 2.1.X 可以直接使用
    大版本，报错，在 Security 5.0+ 增加了很多加密方式
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("test")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("vip1","vip2")
                .and()
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("vip1","vip2", "vip3");
    }
}