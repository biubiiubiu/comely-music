package com.example.comelymusic.generate.common.utils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.example.comelymusic.generate.dto.AuthTokens;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisUtilsTest {
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    AssumeRoleResponse roleResponse;

    @Test
    public void setJsonStringTest() {
        AuthTokens authTokens = new AuthTokens();
        authTokens.setUsername("zt001");
        authTokens.setUserToken(jwtUtils.createToken("zt001"));
        authTokens.setOssToken(roleResponse.getCredentials().getSecurityToken());
        redisUtils.setObject(authTokens.getUsername(), authTokens, 86400L);
        AuthTokens tokens = (AuthTokens) redisUtils.getObject("zt001", AuthTokens.class);
        System.out.println(JSON.toJSONString(tokens));
    }
}