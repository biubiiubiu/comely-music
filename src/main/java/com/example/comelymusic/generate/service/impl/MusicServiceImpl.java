package com.example.comelymusic.generate.service.impl;

import com.example.comelymusic.generate.controller.requests.music.MusicCreateRequest;
import com.example.comelymusic.generate.entity.Music;
import com.example.comelymusic.generate.mapper.MusicMapper;
import com.example.comelymusic.generate.service.MusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌曲表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {
    @Autowired
    private MusicMapper musicMapper;

    @Override
    public int create(MusicCreateRequest musicCreateRequest) {

        return 0;
    }
}
