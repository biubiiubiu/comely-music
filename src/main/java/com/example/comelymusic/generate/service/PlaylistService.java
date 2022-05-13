package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.controller.requests.PlaylistCreateRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistUpdateRequest;
import com.example.comelymusic.generate.entity.Playlist;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 歌单表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
public interface PlaylistService extends IService<Playlist> {

    int create(PlaylistCreateRequest request);

    int deletePlaylist(PlaylistSelectRequest request);

    int updatePlaylist(PlaylistUpdateRequest request);

    Playlist selectPlaylist(PlaylistSelectRequest request);

    void addMusicNum(String playlistId, int addNum);
}
