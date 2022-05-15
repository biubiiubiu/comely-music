package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.PlaylistCreateRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistUpdateRequest;
import com.example.comelymusic.generate.controller.responses.MusicSelectResponse;
import com.example.comelymusic.generate.controller.responses.PlaylistInfoWithMusicListResponse;
import com.example.comelymusic.generate.controller.responses.UserPlaylistsSelectResponse;
import com.example.comelymusic.generate.entity.Music;
import com.example.comelymusic.generate.entity.Playlist;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.enums.UserPlaylistRelation;
import com.example.comelymusic.generate.service.MusicService;
import com.example.comelymusic.generate.service.PlaylistMusicService;
import com.example.comelymusic.generate.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 歌单表 前端控制器
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/generate/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistMusicService playlistMusicService;

    @Autowired
    private MusicService musicService;

    /**
     * 新增歌单
     */
    @PostMapping("/create")
    public R create(@Validated @RequestBody PlaylistCreateRequest request) {
        int result = playlistService.create(request);
        if (result == -1) {
            return R.setResult(ResultCode.PLAYLIST_CREATE_DUPLICATE_ERROR);
        }
        if (result == 0) {
            return R.setResult(ResultCode.PLAYLIST_CREATE_ERROR);
        }
        return R.ok();
    }

    /**
     * 删除歌单
     */
    @PostMapping("/delete")
    public R delete(@Validated @RequestBody PlaylistSelectRequest request) {
        if (request.getUsername() == null || request.getUsername().length() == 0
                || request.getPlaylistName() == null || request.getPlaylistName().length() == 0) {
            return R.setResult(ResultCode.PLAYLIST_NOT_EXIST);
        }
        int delete = playlistService.deletePlaylist(request);
        if (delete == 0) {
            return R.setResult(ResultCode.PLAYLIST_NOT_EXIST);
        }
        return R.ok();
    }

    /**
     * 修改歌单
     */
    @PostMapping("/update")
    public R update(@Validated @RequestBody PlaylistUpdateRequest request) {
        int result = playlistService.updatePlaylist(request);
        if (result == -1) {
            return R.setResult(ResultCode.PLAYLIST_NOT_EXIST);
        }
        if (result == 0) {
            return R.setResult(ResultCode.PLAYLIST_CREATE_ERROR);
        }
        return R.ok();
    }

    @PostMapping("/add-music-into-playlist")
    public R addMusicToPlaylist(@Validated @RequestBody PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getPlaylistName() == null || request.getUsername().length() == 0 || request.getPlaylistName().length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        int result = playlistService.addMusic2Playlist(request);
        if (result == -1) {
            // 歌单不存在
            return R.setResult(ResultCode.PLAYLIST_NOT_EXIST);
        }
        return R.ok().message("成功加入的歌曲数量：" + result);
    }

    @PostMapping("/delete-music-from-playlist")
    public R deleteMusicFromPlaylist(@Validated @RequestBody PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getPlaylistName() == null || request.getUsername().length() == 0 || request.getPlaylistName().length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        int result = playlistService.deleteMusicfromPlaylist(request);
        if (result == -1) {
            // 歌单不存在
            return R.setResult(ResultCode.PLAYLIST_NOT_EXIST);
        }
        return R.ok().message("成功删除的歌曲数量：" + result);
    }

    /**
     * 查询用户创建的所有歌单info
     *
     * @param username username
     * @return UserPlaylistsSelectResponse
     */
    @GetMapping("/select-created-playlists/{username}")
    public R selectAllCreatedPlaylistByUsername(@PathVariable("username") String username) {
        if (username == null || username.length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        List<Playlist> list = playlistService.selectPlaylists(username, UserPlaylistRelation.CREATE.getRelation());
        UserPlaylistsSelectResponse response = new UserPlaylistsSelectResponse();
        List<UserPlaylistsSelectResponse.PlaylistInfo> models = new ArrayList<>();
        for (Playlist playlist : list) {
            UserPlaylistsSelectResponse.PlaylistInfo playlistInfo = playlistService.transPlaylist2PlaylistInfo(playlist);
            models.add(playlistInfo);
        }
        response.setPlaylistInfoList(models);
        return R.ok().data(response);
    }

    @PostMapping("/select-playlist-with-music-list")
    public R selectPlaylistWithMusicList(@Validated @RequestBody PlaylistSelectRequest request) {
        PlaylistInfoWithMusicListResponse response = new PlaylistInfoWithMusicListResponse();
        Playlist playlist = playlistService.selectPlaylist(request);
        if (playlist == null) {
            return R.setResult(ResultCode.PLAYLIST_NOT_EXIST);
        }
        UserPlaylistsSelectResponse.PlaylistInfo playlistInfo = playlistService.transPlaylist2PlaylistInfo(playlist);
        response.setPlaylistInfo(playlistInfo);

        if (playlistInfo.getMusicNum() > 0) {
            List<String> musicIdList = playlistMusicService.selectMusicIdsByPlaylistId(playlist.getId());
            List<Music> musicList = musicService.selectBatchIds(musicIdList);
            List<MusicSelectResponse.MusicInfo> musicInfos = musicService.transMusiclist2MusicinfoList(musicList);
            response.setMusicInfoList(musicInfos);
        }
        return R.ok().data(response);
    }

}

