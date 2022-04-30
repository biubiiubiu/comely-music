package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.ArtistCreateRequest;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 歌手表 前端控制器
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/generate/artist")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    /**
     * 新增Artist
     */
    @PostMapping("/create")
    public R create(@Validated @RequestBody ArtistCreateRequest artistCreateRequest) {
        int result = artistService.create(artistCreateRequest);
        if (result == 0) {
            return R.setResult(ResultCode.ARTIST_CREATE_ERROR);
        }
        return R.ok();
    }
}

