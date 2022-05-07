package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.common.ComelyMusicException;
import com.example.comelymusic.generate.controller.requests.LoginRequest;
import com.example.comelymusic.generate.controller.requests.UserCreateRequest;
import com.example.comelymusic.generate.controller.responses.LoginResponse;
import com.example.comelymusic.generate.controller.responses.UserInfoResponse;
import com.example.comelymusic.generate.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
public interface UserService extends IService<User> {
    int create(UserCreateRequest userCreateRequest) throws ComelyMusicException;
    int deleteByUsername(String username) throws ComelyMusicException;
    UserInfoResponse selectUserInfoByUsername(String username) throws ComelyMusicException;
    int update(UserCreateRequest userCreateRequest) throws ComelyMusicException;

    Boolean judgeNewUser(LoginRequest request);
    LoginResponse loginOrRegister(LoginRequest loginRequest);
    void logout(String username);
    boolean getLoginStatus(String username);

    User selectByUsername(String username);
}
