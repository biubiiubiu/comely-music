package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.controller.requests.ArtistCreateRequest;
import com.example.comelymusic.generate.entity.Artist;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 歌手表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
public interface ArtistService extends IService<Artist> {

    int create(ArtistCreateRequest artistCreateRequest);

    Artist selectByArtistName(String artistName);
}
