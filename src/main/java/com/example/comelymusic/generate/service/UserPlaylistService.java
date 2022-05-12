package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.entity.Playlist;
import com.example.comelymusic.generate.entity.UserPlaylist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-05-12
 */
public interface UserPlaylistService extends IService<UserPlaylist> {
    int create(String playlistName, String username, Integer relation);

    int delete(String playlistName, String username);

    List<Playlist> selectPlaylists(String username, Integer relation);
}
