package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.comelymusic.generate.controller.requests.music.MusicCreateRequest;
import com.example.comelymusic.generate.controller.requests.music.MusicSelectRequest;
import com.example.comelymusic.generate.controller.responses.music.MusicSelectResponse;
import com.example.comelymusic.generate.entity.Artist;
import com.example.comelymusic.generate.entity.FileEntity;
import com.example.comelymusic.generate.entity.Music;
import com.example.comelymusic.generate.enums.PlayerModule;
import com.example.comelymusic.generate.mapper.ArtistMapper;
import com.example.comelymusic.generate.mapper.FileMapper;
import com.example.comelymusic.generate.mapper.MusicMapper;
import com.example.comelymusic.generate.service.MusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ArtistMapper artistMapper;

    @Autowired
    private FileMapper fileMapper;

    @Override
    public int create(MusicCreateRequest musicCreateRequest) {

        return 0;
    }

    /**
     * 随机查询num条满足播放模式条件的歌曲
     */
    @Override
    public MusicSelectResponse selectByModule(MusicSelectRequest request) {
        int num = request.getNum();
        MusicSelectResponse response = new MusicSelectResponse();
        List<MusicSelectResponse.MusicInfo> responseList = new ArrayList<>();
        PlayerModule module = request.getModule();
        QueryWrapper<Music> wrapper = new QueryWrapper<>();
        wrapper.eq("player_module", module);
        List<Music> musicList = musicMapper.selectList(wrapper);
        responseList = music2MusicResponse(musicList, num);
        response.setMusicList(responseList);
        return response;
    }

    private List<MusicSelectResponse.MusicInfo> music2MusicResponse(List<Music> musicList, int num) {
        if (musicList == null) {
            return null;
        }
        int total = Math.min(musicList.size(), num);
        List<MusicSelectResponse.MusicInfo> responseList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Music music = musicList.get(i);
            Artist artist = artistMapper.selectById(music.getArtistId());
            String artistName = artist == null ? null : artist.getName();
            FileEntity cover = fileMapper.selectById(music.getCoverId());
            FileEntity mp3 = fileMapper.selectById(music.getMp3Id());
            FileEntity lyric = fileMapper.selectById(music.getLyricId());
            String coverPath = cover == null ? null : cover.getStorageUrl();
            String audioPath = mp3 == null ? null : mp3.getStorageUrl();
            String lyricPath = lyric == null ? null : lyric.getStorageUrl();
            MusicSelectResponse.MusicInfo info =
                    new MusicSelectResponse.MusicInfo(music.getName(), artistName, audioPath, coverPath, lyricPath);
            responseList.add(info);
        }
        return responseList;
    }
}
