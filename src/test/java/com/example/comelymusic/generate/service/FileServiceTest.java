package com.example.comelymusic.generate.service;

import com.aliyun.oss.ClientException;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
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
            AssumeRoleResponse response = ossTokenResponse.getResponse();
            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + response.getRequestId());
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error message: " + e.getMessage());
        }
    }
}