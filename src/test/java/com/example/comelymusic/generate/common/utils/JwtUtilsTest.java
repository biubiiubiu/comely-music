package com.example.comelymusic.generate.common.utils;

import com.alibaba.fastjson.JSON;
import com.example.comelymusic.generate.controller.requests.file.FileCommonRequest;
import com.example.comelymusic.generate.controller.requests.file.FileUploadRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class JwtUtilsTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void createTokenTest() {
        String token = jwtUtils.createToken("ztian001");
        System.out.println("token: " + token);
        Date date = new Date();
        boolean b = jwtUtils.judgeTokenExpiration(date);
        System.out.println(b);
        String subject = jwtUtils.parseToken(token).getSubject();
        Date expiration = jwtUtils.parseToken(token).getExpiration();
        System.out.println("subject:" + subject);
        System.out.println("expiration:" + expiration);
    }

    @Test
    void getJson() {
        FileCommonRequest request = new FileCommonRequest();
        FileCommonRequest.CommonInfo commonInfo = new FileCommonRequest.CommonInfo();
        commonInfo.setFilename("etst.mp3");
        commonInfo.setSize(1234L);
        commonInfo.setStorageUrl("aksuhd");
        Map<String, FileCommonRequest.CommonInfo> map = new HashMap<>();
        map.put("aaa", commonInfo);
        request.setFileKeyInfoMap(map);
        String s = JSON.toJSONString(request);
        System.out.println(s);
    }
}