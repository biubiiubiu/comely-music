package com.example.comelymusic.generate.common.utils;

import com.alibaba.fastjson.JSON;
import com.example.comelymusic.generate.controller.requests.FileUploadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    void getJson(){
        FileUploadRequest request = new FileUploadRequest();
        request.setUsername("zt001");
        List<FileUploadRequest.FileUploadInfo> list = new ArrayList<>();
        list.add(new FileUploadRequest.FileUploadInfo("稻香.mp3",1111L));
        list.add(new FileUploadRequest.FileUploadInfo("稻香1.mp3",2222L));
        request.setFileUploadInfoList(list);
        String s = JSON.toJSONString(request);
        System.out.println(s);
    }
}