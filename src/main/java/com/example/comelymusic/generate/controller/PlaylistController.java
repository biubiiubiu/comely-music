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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        if (result == -2) {
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
        List<Music> musicList = playlistService.addMusic2Playlist(request);
        return dealWithAddOrRemovePlaylistResult(musicList);
    }

    @PostMapping("/delete-music-from-playlist")
    public R deleteMusicFromPlaylist(@Validated @RequestBody PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getPlaylistName() == null || request.getUsername().length() == 0 || request.getPlaylistName().length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        List<Music> successList = playlistService.deleteMusicfromPlaylist(request);
        return dealWithAddOrRemovePlaylistResult(successList);
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
        if (list == null) {
            return R.ok().data(new UserPlaylistsSelectResponse());
        }
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
        Playlist playlist = playlistService.selectPlaylist(request);
        if (playlist == null) {
            return R.setResult(ResultCode.PLAYLIST_NOT_EXIST);
        }
        PlaylistInfoWithMusicListResponse response = transPlaylist2PlaylistInfoWithMusicListResponse(playlist);
        return R.ok().data(response);
    }

    @PostMapping("/add-music-into-my-like")
    public R addMusicToMylike(@Validated @RequestBody PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getUsername().length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        List<Music> successList = playlistService.addMusic2Mylike(request);
        return dealWithAddOrRemovePlaylistResult(successList);
    }

    @PostMapping("/remove-music-from-my-like")
    public R removeMusicFromMylike(@Validated @RequestBody PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getUsername().length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        List<Music> successList = playlistService.removeFromMylike(request);
        return dealWithAddOrRemovePlaylistResult(successList);
    }

    @PostMapping("/add-music-into-recently-play")
    public R addMusicToRecentlyPlay(@Validated @RequestBody PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getUsername().length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        List<Music> successList = playlistService.addMusic2RecentlyPlay(request);
        return dealWithAddOrRemovePlaylistResult(successList);
    }

    @PostMapping("/remove-music-from-recently-play")
    public R removeMusicFromRecentlyPlay(@Validated @RequestBody PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getUsername().length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        List<Music> successList = playlistService.removeFromRecentlyPlay(request);
        return dealWithAddOrRemovePlaylistResult(successList);
    }

//    @GetMapping("/fuzzy-search-playlist-limit/{searchContent}")
//    public R fuzzySearchPlaylistLimit(@PathVariable("searchContent") String searchContent) {
//        if (searchContent == null || searchContent.length() == 0) {
//            return R.setResult(ResultCode.PARAM_ERROR);
//        }
//        UserPlaylistsSelectResponse response = new UserPlaylistsSelectResponse();
//        List<UserPlaylistsSelectResponse.PlaylistInfo> infos = playlistService.fuzzySearchPlaylist(searchContent);
//        response.setPlaylistInfoList(infos);
//        return R.ok().data(response);
//    }

    @GetMapping("/fuzzy-search-playlist/{searchContent}")
    public R fuzzySearchPlaylistWithMusicList(@PathVariable("searchContent") String searchContent) {
        List<PlaylistInfoWithMusicListResponse> responseList = new ArrayList<>();
        if (searchContent == null || searchContent.length() == 0) {
            return R.setResult(ResultCode.PARAM_ERROR);
        }
        List<Playlist> playlists = playlistService.fuzzySearchPlaylist(searchContent);
        if (playlists != null) {
            for (Playlist pl:playlists){
                PlaylistInfoWithMusicListResponse res = transPlaylist2PlaylistInfoWithMusicListResponse(pl);
                responseList.add(res);
            }
        }
        return R.ok().data(responseList);
    }


    private R dealWithAddOrRemovePlaylistResult(List<Music> successList) {
        if (successList == null) {
            // 歌单不存在
            return R.setResult(ResultCode.PLAYLIST_NOT_EXIST);
        }
        List<MusicSelectResponse.MusicInfo> musicInfos = musicService.transMusiclist2MusicinfoList(successList);
        MusicSelectResponse response = new MusicSelectResponse();
        response.setMusicList(musicInfos);
        return R.ok().data(response);
    }

    private PlaylistInfoWithMusicListResponse transPlaylist2PlaylistInfoWithMusicListResponse(Playlist playlist) {
        PlaylistInfoWithMusicListResponse response = new PlaylistInfoWithMusicListResponse();
        UserPlaylistsSelectResponse.PlaylistInfo playlistInfo = playlistService.transPlaylist2PlaylistInfo(playlist);
        response.setPlaylistInfo(playlistInfo);

        if (playlistInfo.getMusicNum() > 0) {
            List<String> musicIdList = playlistMusicService.selectMusicIdsByPlaylistIdSortByUpdateTime(playlist.getId());
            List<Music> musicList = musicService.selectBatchIds(musicIdList);

            // 对musicList按照musicIdList排序
            Map<String, Music> id2MusicMap = new HashMap<>();
            for (Music music : musicList) {
                id2MusicMap.put(music.getId(), music);
            }
            List<Music> sortedList = new ArrayList<>();
            for (String musicId : musicIdList) {
                sortedList.add(id2MusicMap.get(musicId));
            }
            // 排序之后截取20个
            if (sortedList.size() >= 20) {
                // 只返回最近20个播放记录
                sortedList = sortedList.subList(0, 20);
            }
            List<MusicSelectResponse.MusicInfo> musicInfos = musicService.transMusiclist2MusicinfoList(sortedList);
            response.setMusicInfoList(musicInfos);
        }
        return response;
    }

}

