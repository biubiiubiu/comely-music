package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.music.MusicCreateRequest;
import com.example.comelymusic.generate.controller.requests.user.UserCreateRequest;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 歌曲表 前端控制器
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/generate/music")
public class MusicController {
    @Autowired
    MusicService musicService;
    /**
     * 新增用户
     */
    @PostMapping("/create")
    public R create(@Validated @RequestBody MusicCreateRequest musicCreateRequest) {
        int result = musicService.create(musicCreateRequest);
        if (result == 0) {
            return R.setResult(ResultCode.USERNAME_EXISTS);
        }
        return R.ok();
    }
}

