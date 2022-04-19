package com.example.comelymusic.generate.controller.requests.user;

import lombok.Data;

/**
 * description: 登录数据规范
 *
 * @author: zhangtian
 * @since: 2022-04-18 20:58
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
