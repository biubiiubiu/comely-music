package com.example.comelymusic.generate.controller.requests;

import lombok.Data;

/**
 * description: 歌单创建约束
 *
 * @author: zhangtian
 * @since: 2022-05-07 10:40
 */
@Data
public class PlaylistCreateRequest {
    private String name;
    private String username;
    private String coverId;
    private Integer musicNum;
    private String description;
    private String status;

    public PlaylistCreateRequest setName(String name) {
        this.name = name;
        return this;
    }

    public PlaylistCreateRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public PlaylistCreateRequest setCoverId(String coverId) {
        this.coverId = coverId;
        return this;
    }

    public PlaylistCreateRequest setMusicNum(Integer musicNum) {
        this.musicNum = musicNum;
        return this;
    }

    public PlaylistCreateRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public PlaylistCreateRequest setStatus(String status) {
        this.status = status;
        return this;
    }
}
