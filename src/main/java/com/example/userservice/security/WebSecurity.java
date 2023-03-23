package com.example.userservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    //권한 관련 메소드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests().antMatchers("/users").permitAll(); //'/users'로 들어오는 것은 허락해줘
        http.authorizeHttpRequests().antMatchers("/h2-console/**").permitAll();

        http.headers().frameOptions().disable();
        // h2 db 접속 원활하도록 설정
    }
}
