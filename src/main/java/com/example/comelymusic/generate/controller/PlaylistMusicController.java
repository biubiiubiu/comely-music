package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.service.PlaylistMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 歌单歌曲表 前端控制器
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/generate/playlist_music")
public class PlaylistMusicController {
    @Autowired
    private PlaylistMusicService playlistMusicService;

    /**
     * 把歌曲添加进歌单
     */
    @PostMapping("/add-music")
    public R addMusic(@Validated @RequestBody PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getPlaylistName() == null || request.getUsername().length() == 0 || request.getPlaylistName().length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        int result = playlistMusicService.addMusic2Playlist(request);
        if (result == -1) {
            // 歌单不存在
            return R.setResult(ResultCode.PLAYLIST_NOT_EXIST);
        }
        return R.ok().message("成功加入的歌曲数量：" + result);
    }
}

