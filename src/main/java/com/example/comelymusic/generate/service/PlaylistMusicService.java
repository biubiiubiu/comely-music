package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.entity.PlaylistMusic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 歌单歌曲表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
public interface PlaylistMusicService extends IService<PlaylistMusic> {
    /**
     * 查询某个歌单的所有music id，根据updateTime排序
     */
    List<String> selectMusicIdsByPlaylistIdSortByUpdateTime(String playlistId);
}
