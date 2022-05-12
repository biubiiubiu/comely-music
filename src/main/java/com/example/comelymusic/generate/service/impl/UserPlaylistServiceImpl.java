package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.entity.Playlist;
import com.example.comelymusic.generate.entity.User;
import com.example.comelymusic.generate.entity.UserPlaylist;
import com.example.comelymusic.generate.mapper.UserPlaylistMapper;
import com.example.comelymusic.generate.service.PlaylistService;
import com.example.comelymusic.generate.service.UserPlaylistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.comelymusic.generate.service.UserService;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-05-12
 */
@Service
public class UserPlaylistServiceImpl extends ServiceImpl<UserPlaylistMapper, UserPlaylist> implements UserPlaylistService {

    @Autowired
    private UserPlaylistMapper mapper;

    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private PlaylistService playlistService;

    /**
     * 创建用户-歌单 关系
     *
     * @param playlistName 歌单名
     * @param username     用户名
     * @param relation     关系，0-我喜欢，1-创建，2-收藏
     * @return 创建结果
     */
    @Override
    public int create(String playlistName, String username, Integer relation) {
        UserPlaylist entity = new UserPlaylist();
        User user = userService.selectByUsername(username);
        String userid = user.getId();
        Playlist playlist = playlistService.selectPlaylist(new PlaylistSelectRequest().setUsername(username).setPlaylistName(playlistName));
        String playlistId = playlist.getId();
        if (checkoutDuplicate(playlistId, userid)) {
            // 用户-歌单名 唯一
            return -1;
        }
        entity.setUserId(userid);
        entity.setPlaylistId(playlistId);
        entity.setRelationMap(relation);
        return mapper.insert(entity);
    }

    @Override
    public int delete(String playlistName, String username) {
        Playlist playlist = playlistService.selectPlaylist(new PlaylistSelectRequest().setUsername(username).setPlaylistName(playlistName));
        if (playlist == null) {
            return 0;
        }
        String playlistId = playlist.getId();
        User user = userService.selectByUsername(username);
        String userid = user.getId();
        QueryWrapper<UserPlaylist> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);
        wrapper.eq("playlist_id", playlistId);
        return mapper.delete(wrapper);
    }

    private boolean checkoutDuplicate(String playlistId, String userid) {
        QueryWrapper<UserPlaylist> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);
        wrapper.eq("playlist_id", playlistId);
        UserPlaylist playlist = mapper.selectOne(wrapper);
        return playlist != null;
    }
}
