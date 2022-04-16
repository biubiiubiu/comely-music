package com.example.comelymusic.generate.dto;

import lombok.Data;

/**
 * description: 存储在redis中的临时信息，包括用户token、ossToken
 * 为空表示未登录
 *
 * @author: zhangtian
 * @since: 2022-04-16 16:25
 */
@Data
public class AuthTokens {
    private String username;
    private String userToken;
    private String ossToken;
}
