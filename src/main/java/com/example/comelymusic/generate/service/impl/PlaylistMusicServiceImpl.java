package com.example.comelymusic.generate.service.impl;

import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.entity.Music;
import com.example.comelymusic.generate.entity.Playlist;
import com.example.comelymusic.generate.entity.PlaylistMusic;
import com.example.comelymusic.generate.mapper.MusicMapper;
import com.example.comelymusic.generate.mapper.PlaylistMusicMapper;
import com.example.comelymusic.generate.service.MusicService;
import com.example.comelymusic.generate.service.PlaylistMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.comelymusic.generate.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private MusicService musicService;

    @Autowired
    private PlaylistService playlistService;

    /**
     * 添加歌曲到歌单
     * @param request
     * @return
     */
    @Override
    public int addMusic2Playlist(PlaylistMusicAddRequest request) {
        List<Music> musicList = musicService.getMusicListByMusicAddInfoList(request.getMusicAddInfoList());

        Playlist playlist = playlistService.selectPlaylist(new PlaylistSelectRequest()
                .setUsername(request.getUsername()).setPlaylistName(request.getPlaylistName()));

        String playlistId = playlist.getId();

        int total = 0;
        for (Music music : musicList) {
            total += mapper.insert(new PlaylistMusic().setMusic_id(music.getId()).setPlaylistId(playlistId));
        }
        // 修改playlist歌曲数量
        playlistService.addMusicNum(playlistId, playlist.getMusicNum() + total);
        return total;
    }
}
