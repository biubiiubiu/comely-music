package com.example.comelymusic.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.comelymusic.generate.controller.requests.PlaylistCreateRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistUpdateRequest;
import com.example.comelymusic.generate.controller.responses.UserPlaylistsSelectResponse;
import com.example.comelymusic.generate.entity.Music;
import com.example.comelymusic.generate.entity.Playlist;

import java.util.List;

/**
 * <p>
 * 歌单表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
public interface PlaylistService extends IService<Playlist> {

    /**
     * （创建歌单,并创建用户歌单关系,默认时创建关系，如果是收藏需要设置relation），原子操作，返回结果
     */
    int create(PlaylistCreateRequest request);

    /**
     * 创建用户喜欢歌单
     */
    int createMyLike(String username);

    /**
     * 根据创建用户username和歌单名称来删除歌单,并删除用户-歌单关系
     */
    int deletePlaylist(PlaylistSelectRequest request);

    /**
     * 修改歌单信息，旧的歌单名+创建者，新的歌单信息
     */
    int updatePlaylist(PlaylistUpdateRequest request);

    /**
     * 根据创建者用户名和歌单名查询歌单信息
     */
    Playlist selectPlaylist(PlaylistSelectRequest request);

    /**
     * 增加歌曲数量
     */
    void addMusicNum(String playlistId, int addNum);

    /**
     * 把music加入歌单
     */
    List<Music> addMusic2Playlist(PlaylistMusicAddRequest request);

    /**
     * 查询user所有relation（创建、收藏、喜欢）的歌单
     */
    List<Playlist> selectPlaylists(String username, Integer relation);

    /**
     * playlist转换成playlistInfo
     */
    UserPlaylistsSelectResponse.PlaylistInfo transPlaylist2PlaylistInfo(Playlist playlist);

    /**
     * 把music从歌单删除
     */
    int deleteMusicfromPlaylist(PlaylistMusicAddRequest request);

    /**
     * 把music加入”我喜欢“歌单
     */
    List<Music> addMusic2Mylike(PlaylistMusicAddRequest request);

}
