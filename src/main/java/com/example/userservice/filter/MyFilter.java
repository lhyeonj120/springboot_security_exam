package com.example.userservice.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.JwtSignatureValidator;
import org.springframework.core.env.Environment;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

public class MyFilter implements Filter {
    private Environment env;

    public MyFilter(Environment env){
        this.env = env;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        System.out.println("필터 호출");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getHeader("AUTHORIZATION") == null){
            onError(response, "UNAUTHORIZATION");
        }else{
            String authorizationHeader = request.getHeader("AUTHORIZATION");
            System.out.println(authorizationHeader);
            String jwt = authorizationHeader.replace("Bearer", "");

            if(!isJwtValid(jwt)){
                onError(response, "UNAUTHORIZATION2");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String subject = null;

        try{
            subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(jwt).getBody().getSubject();
        }catch (Exception e){
            returnValue = false;
        }

        if(subject == null || subject.isEmpty()){
            returnValue = false;
        }

        return returnValue;
    }

    //에러 관련 처리 로직
    private void onError(HttpServletResponse response, String httpStatus) throws IOException{
        response.addHeader("error", httpStatus);
        response.sendError(HttpServletResponse.SC_FORBIDDEN, httpStatus);
    }
}
