package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.MusicCreateRequest;
import com.example.comelymusic.generate.controller.requests.MusicSelectByModuleRequest;
import com.example.comelymusic.generate.controller.requests.MusicSelectByTagsRequest;
import com.example.comelymusic.generate.controller.responses.MusicBatchCreateResponse;
import com.example.comelymusic.generate.controller.responses.MusicSelectResponse;
import com.example.comelymusic.generate.entity.Music;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
     * 新增音乐
     */
    @PostMapping("/create")
    public R create(@Validated @RequestBody MusicCreateRequest musicCreateRequest) {
        int result = musicService.create(musicCreateRequest);
        if (result == 0) {
            return R.setResult(ResultCode.MUSIC_CREATE_ERROR);
        } else if (result == -1) {
            return R.setResult(ResultCode.MUSIC_EXISTS);
        }
        return R.ok();
    }

    /**
     * 批量新增音乐
     */
    @PostMapping("/batch-create")
    public R batchCreate(@Validated @RequestBody List<MusicCreateRequest> requestList) {
        MusicBatchCreateResponse response = musicService.batchCreate(requestList);
        return R.ok().data(response);
    }

    /**
     * 根据播放模式查询
     */
    @PostMapping("/get-list")
    public R getMusicListByModule(@Validated @RequestBody MusicSelectByModuleRequest musicSelectByModuleRequest) {
        List<Music> musicList = musicService.selectByModule(musicSelectByModuleRequest);
        MusicSelectResponse response = new MusicSelectResponse();
        List<MusicSelectResponse.MusicInfo> musicInfos = musicService.transMusiclist2MusicinfoList(musicList);
        response.setMusicList(musicInfos);
        return R.ok().data(response);
    }

    /**
     * 根据歌名模糊搜索
     */
    @GetMapping("/fuzzy-search-name/{name}")
    public R fuzzySearchMusicByName(@PathVariable("name") String name) {
        List<Music> musicList = musicService.fuzzySearch(name);
        List<String> tags = new ArrayList<>();
        tags.add(name);
        List<Music> musicListByTag = musicService.selectByTags(tags, 50);
        Set<Music> set = new HashSet<>(musicList);
        set.addAll(musicListByTag);
        List<Music> resultList = new ArrayList<>(set);
        MusicSelectResponse response = new MusicSelectResponse();
        List<MusicSelectResponse.MusicInfo> musicInfos = musicService.transMusiclist2MusicinfoList(resultList);
        response.setMusicList(musicInfos);
        return R.ok().data(response);
    }

    /**
     * 根据歌名模糊搜索,只返回十个结果
     */
    @GetMapping("/fuzzy-search-name-limit/{name}")
    public R fuzzySearchMusicByNameLimit(@PathVariable("name") String name) {
        List<Music> musicList = musicService.fuzzySearch(name);
        List<String> tags = new ArrayList<>();
        tags.add(name);
        List<Music> musicListByTag = musicService.selectByTags(tags, 7);
        Set<Music> set = new HashSet<>(musicList);
        set.addAll(musicListByTag);
        List<Music> resultList = new ArrayList<>(set);
        int right = Math.min(resultList.size(), 7);
        List<Music> limitMusicList = resultList.subList(0, right);
        MusicSelectResponse response = new MusicSelectResponse();
        List<MusicSelectResponse.MusicInfo> musicInfos = musicService.transMusiclist2MusicinfoList(limitMusicList);
        response.setMusicList(musicInfos);
        return R.ok().data(response);
    }

    @PostMapping("/get-list-by-tags")
    public R getMusicByTags(@Validated @RequestBody MusicSelectByTagsRequest request) {
        List<String> tags = request.getTags();
        int num = request.getNum();
        if (tags == null || tags.size() == 0 || num <= 0) {
            return R.error();
        }
        List<Music> musicList = musicService.selectByTags(tags, num);
        MusicSelectResponse response = new MusicSelectResponse();
        List<MusicSelectResponse.MusicInfo> musicInfos = musicService.transMusiclist2MusicinfoList(musicList);
        response.setMusicList(musicInfos);
        return R.ok().data(response);
    }
}

