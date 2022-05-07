package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.comelymusic.generate.controller.requests.PlaylistCreateRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistUpdateRequest;
import com.example.comelymusic.generate.entity.Playlist;
import com.example.comelymusic.generate.entity.PlaylistMusic;
import com.example.comelymusic.generate.entity.User;
import com.example.comelymusic.generate.mapper.PlaylistMapper;
import com.example.comelymusic.generate.mapper.PlaylistMusicMapper;
import com.example.comelymusic.generate.service.PlaylistService;
import com.example.comelymusic.generate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 歌单表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Service
public class PlaylistServiceImpl extends ServiceImpl<PlaylistMapper, Playlist> implements PlaylistService {
    @Autowired
    private PlaylistMapper playlistMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PlaylistMusicMapper playlistMusicMapper;

    /**
     * 创建歌单
     */
    @Override
    public int create(PlaylistCreateRequest request) {
        Playlist playlist = createRequest2Entity(request);
        return playlistMapper.insert(playlist);
    }

    /**
     * 根据创建用户username和歌单名称来删除歌单
     */
    @Override
    public int deletePlaylist(PlaylistSelectRequest request) {
        Playlist playlist = selectPlaylist(request);
        Integer musicNum = playlist.getMusicNum();
        int delete = playlistMapper.deleteById(playlist.getId());

        int deleteMusicNum = deletePlaylistMusics(playlist.getId());
        if (deleteMusicNum == musicNum) {
            // 删除了全部歌单-歌曲关联
            return delete + 1;
        } else {
            // 未删除完全
            return delete;
        }
    }

    /**
     * 修改歌单信息,旧的歌单名创建者，新的歌单信息
     */
    @Override
    public void updatePlaylist(PlaylistUpdateRequest request) {
        Playlist playlist = selectPlaylist(new PlaylistSelectRequest().setPlaylistName(request.getOldName()).setUsername(request.getOldUsername()));
        if (playlist != null) {
            if (request.getNewName() != null && !request.getNewName().equals(playlist.getName())) {
                playlist.setName(request.getNewName());
            }
            if (request.getNewUsername() != null) {
                User user = userService.selectByUsername(request.getNewUsername());
                if (user != null && !user.getId().equals(playlist.getCreatedByUserId())) {
                    playlist.setCreatedByUserId(user.getId());
                }
            }
            if (request.getStatus() != null && !request.getStatus().equals(playlist.getStatus())) {
                playlist.setStatus(request.getStatus());
            }
            if (request.getDescription() != null) {
                playlist.setDescription(request.getDescription());
            }
            if (request.getMusicNum() != null) {
                playlist.setMusicNum(request.getMusicNum());
            }
            if (request.getCoverId() != null) {
                playlist.setCoverId(request.getCoverId());
            }
            if (request.getCollectionNum() != null) {
                playlist.setCollectNum(request.getCollectionNum());
            }
            playlistMapper.updateById(playlist);
        }
    }

    /**
     * 根据创建者用户名和歌单名查询歌单信息
     */
    @Override
    public Playlist selectPlaylist(PlaylistSelectRequest request) {
        QueryWrapper<Playlist> wrapper = new QueryWrapper<>();
        wrapper.eq("name", request.getPlaylistName());

        User user = userService.selectByUsername(request.getUsername());
        if (user != null) {
            wrapper.eq("created_by_user_id", user.getId());
        }
        return playlistMapper.selectOne(wrapper);
    }

    /**
     * 增加歌曲数量
     */
    @Override
    public void addMusicNum(String playlistId, int addNum) {
        if (addNum != 0) {
            Playlist playlist = playlistMapper.selectById(playlistId);
            playlist.setMusicNum(playlist.getMusicNum() + addNum);
            playlistMapper.updateById(playlist);
        }
    }

    /**
     * 删除所有与歌单-歌曲关联表信息,返回删除成功条目
     */
    private int deletePlaylistMusics(String playlistId) {
        QueryWrapper<PlaylistMusic> wrapper = new QueryWrapper<>();
        wrapper.eq("playlist_id", playlistId);
        return playlistMusicMapper.delete(wrapper);
    }

    private Playlist createRequest2Entity(PlaylistCreateRequest request) {
        Playlist playlist = new Playlist();
        String name = request.getName();
        String createdUsername = request.getUsername();
        if (name == null || name.length() == 0 || createdUsername == null || createdUsername.length() == 0) {
            return null;
        }
        playlist.setName(name);
        User user = userService.selectByUsername(request.getUsername());
        if (user != null) {
            playlist.setCreatedByUserId(user.getId());
        }
        if (request.getCoverId() != null) {
            playlist.setCoverId(request.getCoverId());
        }
        if (request.getDescription() != null) {
            playlist.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            playlist.setStatus(request.getStatus());
        }
        if (request.getMusicNum() != null) {
            playlist.setMusicNum(request.getMusicNum());
        }
        return playlist;
    }
}
