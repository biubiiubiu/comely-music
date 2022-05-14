package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.entity.PlaylistMusic;
import com.example.comelymusic.generate.mapper.PlaylistMusicMapper;
import com.example.comelymusic.generate.service.PlaylistMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 歌单歌曲表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Service
public class PlaylistMusicServiceImpl extends ServiceImpl<PlaylistMusicMapper, PlaylistMusic> implements PlaylistMusicService {
    @Autowired
    private PlaylistMusicMapper mapper;

    @Override
    public List<String> selectMusicIdsByPlaylistId(String playlistId) {
        QueryWrapper<PlaylistMusic> wrapper = new QueryWrapper<>();
        wrapper.eq("playlist_id", playlistId);
        List<PlaylistMusic> playlistMusics = mapper.selectList(wrapper);
        List<String> musicIdList = new ArrayList<>();
        for (PlaylistMusic pm : playlistMusics) {
            musicIdList.add(pm.getMusicId());
        }
        return musicIdList;
    }

}
