package com.example.userservice.vo;

import lombok.Data;

@Data
public class ResponseUser { //클라이언트가 봐야할 내용만 담은 객체
    private String email;
    private String name;
    private String userId;
}
