package com.example.comelymusic.generate.service;

import com.aliyun.oss.ClientException;
import com.example.comelymusic.generate.controller.responses.OssTokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileServiceTest {
    @Autowired
    FileService fileService;

    @Test
    public void getOssSTSTest(){
        try {
            OssTokenResponse ossTokenResponse = fileService.getOssToken();
            System.out.println("Expiration: " + ossTokenResponse.getExpiration());
            System.out.println("Access Key Id: " + ossTokenResponse.getAccessKeyId());
            System.out.println("Access Key Secret: " + ossTokenResponse.getAccessKeySecret());
            System.out.println("Security Token: " + ossTokenResponse.getSecurityToken());
            System.out.println("RequestId: " + ossTokenResponse.getRequestId());
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error message: " + e.getMessage());
        }
    }
}