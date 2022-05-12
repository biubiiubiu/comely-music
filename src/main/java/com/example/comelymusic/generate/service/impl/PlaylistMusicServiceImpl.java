package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.entity.Music;
import com.example.comelymusic.generate.entity.Playlist;
import com.example.comelymusic.generate.entity.PlaylistMusic;
import com.example.comelymusic.generate.mapper.PlaylistMusicMapper;
import com.example.comelymusic.generate.service.MusicService;
import com.example.comelymusic.generate.service.PlaylistMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.comelymusic.generate.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
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

    @Autowired
    private MusicService musicService;

    @Autowired
    private PlaylistService playlistService;

    /**
     * 添加歌曲到歌单
     */
    @Override
    public int addMusic2Playlist(PlaylistMusicAddRequest request) {
        List<Music> musicList = musicService.getMusicListByMusicAddInfoList(request.getMusicAddInfoList());

        Playlist playlist = playlistService.selectPlaylist(new PlaylistSelectRequest()
                .setUsername(request.getUsername()).setPlaylistName(request.getPlaylistName()));

        if (playlist != null) {
            String playlistId = playlist.getId();
            // 去重
            duplicateRemoval(musicList);

            int total = 0;
            for (Music music : musicList) {
                QueryWrapper<PlaylistMusic> wrapper = new QueryWrapper<>();
                wrapper.eq("playlist_id", playlistId).eq("music_id", music.getId());
                PlaylistMusic existMusic = mapper.selectOne(wrapper);
                if (existMusic != null) {
                    log.warn("重复插入音乐名：" + music.getName());
                    continue;
                }
                total += mapper.insert(new PlaylistMusic().setMusic_id(music.getId()).setPlaylistId(playlistId));
            }
            // 修改playlist歌曲数量
            playlistService.addMusicNum(playlistId, playlist.getMusicNum() + total);
            return total;
        }
        return -1;
    }

    private void duplicateRemoval(List<Music> musicList) {
        Set<Music> set = new HashSet<>(musicList);
        musicList = new ArrayList<>();
        musicList.addAll(set);
    }
}
