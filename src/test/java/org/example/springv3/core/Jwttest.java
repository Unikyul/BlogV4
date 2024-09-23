package org.example.springv3.core;

import org.example.springv3.core.util.JwtUtil;
import org.example.springv3.user.User;
import org.junit.jupiter.api.Test;


public class Jwttest {

    @Test
    public void create_test(){
        User user = User.builder().id(1).username("ssar").build();
        String accessToken = JwtUtil.create(user);
        System.out.println(accessToken);
    }

    // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLrsJTrs7QiLCJpZCI6MSwiZXhwIjoxNzI3NDAxMDc4fQ.1Ajxs0WTdg40m1CNloFCty1adboo5FpE7qMS0-IfICKFO10xO0u_0dn5R-Cfb9kNdramh15HxGg4kyC7cadJfw
    @Test
    public void verify_test(){
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLrsJTrs7QiLCJpZCI6MSwiZXhwIjoxNzI3NDAyMTg5LCJ1c2VybmFtZSI6InNzYXIifQ.FyBOadpjVaXKRKw4XiCwIeWz-J7aP8gleudP-7erCkxa1L4iEEKAWG6g8-zhseEtdKcG9etpPqloJxHEyu56PA";
        User user = JwtUtil.verify(accessToken);
        System.out.println(user.getId());
        System.out.println(user.getUsername());
    }
}
