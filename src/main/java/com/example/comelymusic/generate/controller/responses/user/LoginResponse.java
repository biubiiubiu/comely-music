package com.example.comelymusic.generate.controller.responses.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.comelymusic.generate.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * description: 登录返回结果
 *
 * @author: zhangtian
 * @since: 2022-04-18 21:01
 */
@Data
public class LoginResponse {
    private String username;
    private String nickname;
    private String gender;
    private String avatarId;
    private Integer role;
    private String loginToken;

    public LoginResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginResponse setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public LoginResponse setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public LoginResponse setAvatarId(String avatarId) {
        this.avatarId = avatarId;
        return this;
    }

    public LoginResponse setRole(Integer role) {
        this.role = role;
        return this;
    }

    public LoginResponse setLoginToken(String loginToken) {
        this.loginToken = loginToken;
        return this;
    }
}
