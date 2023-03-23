package com.example.userservice.security;

import com.example.userservice.service.UserService;
import org.apache.catalina.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;

    private WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.env = env;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //권한 관련 메소드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests().antMatchers("/users").permitAll(); //'/users'로 들어오는 것은 허락해줘

        // h2 db 접속 원활하도록 설정
        http.authorizeHttpRequests().antMatchers("/h2-console/**").permitAll();
        http.headers().frameOptions().disable();


        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress("localhost")
                .and()
                .addFilter(getAuthenticationFilter());
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter authenticationFilter
                = new AuthenticationFilter(authenticationManager());
        return authenticationFilter;
    }

    //인증 관련 메소드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //userDetailsService가 id와 password 처리 해주는데 userDetailsService 누구니
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
