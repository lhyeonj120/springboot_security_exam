package com.example.userservice.vo;

import lombok.Data;

@Data
public class RequestUser {  //클라이언트가 입력할 수 있는 내용만 담은 객체
    private String email;
    private String name;
    private String pwd;
}
