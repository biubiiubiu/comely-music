package com.example.comelymusic.generate.controller.responses.user;

import lombok.Data;

/**
 * description: 用户信息
 *
 * @author: zhangtian
 * @since: 2022-04-24 17:20
 */
@Data
public class UserInfoResponse {
    private String username;
    private String nickname;
    private String gender;
    private Integer role;
    private String avatarId;

    public UserInfoResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserInfoResponse setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserInfoResponse setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public UserInfoResponse setRole(Integer role) {
        this.role = role;
        return this;
    }

    public UserInfoResponse setAvatarId(String avatarId) {
        this.avatarId = avatarId;
        return this;
    }
}
