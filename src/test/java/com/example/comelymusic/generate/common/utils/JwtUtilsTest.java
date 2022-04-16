package com.example.comelymusic.generate.common.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class JwtUtilsTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void createTokenTest(){
        String token = jwtUtils.createToken("ztian001");
        System.out.println("token: "+token);
        Date date = new Date();
        boolean b = jwtUtils.judgeTokenExpiration(date);
        System.out.println(b);
        String subject = jwtUtils.parseToken(token).getSubject();
        Date expiration = jwtUtils.parseToken(token).getExpiration();
        System.out.println("subject:"+ subject);
        System.out.println("expiration:"+ expiration);
    }

}