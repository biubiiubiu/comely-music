package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.responses.UserPlaylistsSelectResponse;
import com.example.comelymusic.generate.entity.Playlist;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.enums.UserPlaylistRelation;
import com.example.comelymusic.generate.service.UserPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangtian
 * @since 2022-05-12
 */
@RestController
@RequestMapping("/generate/userPlaylist")
public class UserPlaylistController {

    @Autowired
    private UserPlaylistService service;

    @GetMapping("/select-created-playlists/{username}")
    public R selectAllCreatedPlaylistByUsername(@PathVariable("username") String username) {
        if (username == null || username.length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        List<Playlist> list = service.selectPlaylists(username, UserPlaylistRelation.CREATE.getRelation());
        UserPlaylistsSelectResponse response = new UserPlaylistsSelectResponse();
        List<UserPlaylistsSelectResponse.PlaylistModel> models = list.stream()
                .map(playlist -> new UserPlaylistsSelectResponse.PlaylistModel(playlist.getName(),
                        playlist.getMusicNum())).collect(Collectors.toList());
        response.setPlaylistModelList(models);
        return R.ok().data(response);
    }
}

