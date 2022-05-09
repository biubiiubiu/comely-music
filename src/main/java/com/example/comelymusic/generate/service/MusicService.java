package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.controller.requests.MusicCreateRequest;
import com.example.comelymusic.generate.controller.requests.MusicSelectRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.controller.responses.MusicBatchCreateResponse;
import com.example.comelymusic.generate.controller.responses.MusicSelectResponse;
import com.example.comelymusic.generate.entity.Music;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 歌曲表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
public interface MusicService extends IService<Music> {

    int create(MusicCreateRequest musicCreateRequest);

    MusicSelectResponse selectByModule(MusicSelectRequest musicSelectRequest);

    MusicSelectResponse fuzzySearch(String name);

    List<Music> getMusicListByMusicAddInfoList(List<PlaylistMusicAddRequest.MusicAddInfo> musicAddInfoList);

    MusicBatchCreateResponse batchCreate(List<MusicCreateRequest> requestList);

    List<Music> selectByName(String name);
}
