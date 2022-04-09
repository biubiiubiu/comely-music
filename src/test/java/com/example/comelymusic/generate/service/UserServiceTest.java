package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.controller.createrequest.UserCreateRequest;
import com.example.comelymusic.generate.enums.Gender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void createTest() {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setUsername("zt001");
        userCreateRequest.setNickname("zhangtian001");
        userCreateRequest.setGender(Gender.MALE.getGender());
        userCreateRequest.setPassword("123456");
        int i = userService.create(userCreateRequest);
        System.out.println(i);
    }

    @Test
    public void deleteTest(){
        int i = userService.deleteByUsername("zt001");
        System.out.println(i);
    }
}