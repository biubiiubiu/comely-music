package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.MusicCreateRequest;
import com.example.comelymusic.generate.controller.requests.MusicSelectRequest;
import com.example.comelymusic.generate.controller.responses.MusicSelectResponse;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据播放模式查询
     */
    @PostMapping("/get-list")
    public R getMusicListByModule(@Validated @RequestBody MusicSelectRequest musicSelectRequest){
        MusicSelectResponse response = musicService.selectByModule(musicSelectRequest);
        return R.ok().data(response);
    }

    /**
     * 根据歌名模糊搜索
     */
    @GetMapping("/fuzzy-search-name/{name}")
    public R fuzzySearchMusicByName(@PathVariable("name") String name){
        MusicSelectResponse response =  musicService.fuzzySearch(name);
        return R.ok().data(response);
    }
}

