package com.example.comelymusic.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.comelymusic.generate.controller.requests.MusicCreateRequest;
import com.example.comelymusic.generate.controller.requests.MusicSelectByModuleRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.controller.responses.MusicBatchCreateResponse;
import com.example.comelymusic.generate.controller.responses.MusicSelectResponse;
import com.example.comelymusic.generate.entity.Music;

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

    List<Music> selectByModule(MusicSelectByModuleRequest musicSelectByModuleRequest);

    /**
     * 根据歌名模糊搜索歌曲
     */
    List<Music> fuzzySearch(String musicName);

    List<Music> getMusicListByMusicAddInfoList(List<PlaylistMusicAddRequest.MusicAddInfo> musicAddInfoList);

    MusicBatchCreateResponse batchCreate(List<MusicCreateRequest> requestList);

    List<Music> selectByName(String name);

    List<Music> selectByTags(List<String> tags, int num);

    /**
     * List<Music>经过查询转换成List<MusicSelectResponse.MusicInfo>
     */
    List<MusicSelectResponse.MusicInfo> transMusiclist2MusicinfoList(List<Music> musicList);

    List<Music> selectBatchIds(List<String> musicIdList);

}
