package com.example.userservice.config;

import com.example.userservice.filter.MyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.servlet.FilterRegistration;

@Configuration
public class FilterConfig {
    @Autowired
    private Environment env;

    @Bean
    public FilterRegistrationBean<MyFilter> filter1(){
        FilterRegistrationBean<MyFilter> bean =
                new FilterRegistrationBean<>(new MyFilter(env));

        bean.addUrlPatterns("/user2/*");
        bean.addUrlPatterns("/hello/*");
        bean.setOrder(0);

        return bean;
    }
}
