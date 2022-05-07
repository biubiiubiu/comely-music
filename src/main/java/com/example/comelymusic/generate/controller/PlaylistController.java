package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.PlaylistCreateRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增歌单
     */
    @PostMapping("/create")
    public R create(@Validated @RequestBody PlaylistCreateRequest request) {
        int result = playlistService.create(request);
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
            return R.setResult(ResultCode.PLAYLIST_DELETE_ERROR);
        }
        int delete = playlistService.deletePlaylist(request);
        if (delete == 0) {
            return R.setResult(ResultCode.PLAYLIST_DELETE_ERROR);
        }
        return R.ok();
    }
}

