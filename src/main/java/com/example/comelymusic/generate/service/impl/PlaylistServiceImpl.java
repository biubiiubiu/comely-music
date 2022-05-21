package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.comelymusic.generate.controller.requests.PlaylistCreateRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistUpdateRequest;
import com.example.comelymusic.generate.controller.responses.UserPlaylistsSelectResponse;
import com.example.comelymusic.generate.entity.*;
import com.example.comelymusic.generate.enums.UserPlaylistRelation;
import com.example.comelymusic.generate.mapper.PlaylistMapper;
import com.example.comelymusic.generate.mapper.PlaylistMusicMapper;
import com.example.comelymusic.generate.mapper.UserPlaylistMapper;
import com.example.comelymusic.generate.service.MusicService;
import com.example.comelymusic.generate.service.PlaylistService;
import com.example.comelymusic.generate.service.UserPlaylistService;
import com.example.comelymusic.generate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 歌单表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Service
public class PlaylistServiceImpl extends ServiceImpl<PlaylistMapper, Playlist> implements PlaylistService {
    @Autowired
    private PlaylistMapper playlistMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PlaylistMusicMapper playlistMusicMapper;

    @Lazy
    @Autowired
    private UserPlaylistService userPlaylistService;

    @Lazy
    @Autowired
    private UserPlaylistMapper userPlaylistMapper;

    @Lazy
    @Autowired
    private MusicService musicService;

    @Override
    public int create(PlaylistCreateRequest request) {
        if (request.getUsername() == null || request.getUsername().length() == 0 || request.getName() == null || request.getName().length() == 0) {
            return -1;
        }
        if (checkoutDuplicate(request.getName(), request.getUsername())) {
            // 用户不能创建重复歌单名
            return -2;
        }
        Playlist playlist = createRequest2Entity(request);
        int insertPlaylist = playlistMapper.insert(playlist);
        if (insertPlaylist == 0) {
            return 0;
        }
        if (request.getRelation() == null) {
            request.setRelation(1);
        }
        int insertRelation = userPlaylistService.create(request.getName(), request.getUsername(), request.getRelation());
        if (insertRelation == 0) {
            // 如果关系为创建成功，那删除刚刚创建的歌单，相当于一次原子操作
            deletePlaylist(new PlaylistSelectRequest().setPlaylistName(request.getName()).setUsername(request.getUsername()));
            return 0;
        }
        return 1;
    }

    @Override
    public int createMyLike(String username) {
        PlaylistCreateRequest request = new PlaylistCreateRequest();
        String playlistName = username + "的喜欢歌单";
        request.setUsername(username).setName(playlistName).setVisibility(0).setMusicNum(0).setRelation(UserPlaylistRelation.MY_LIKE.getRelation());
        return create(request);
    }

    @Override
    public int createRecentlyPlay(String username) {
        PlaylistCreateRequest request = new PlaylistCreateRequest();
        String playlistName = username + "的最近播放";
        request.setUsername(username).setName(playlistName).setVisibility(0).setMusicNum(0).setRelation(UserPlaylistRelation.RECENTLY_PLAY.getRelation());
        return create(request);
    }

    private boolean checkoutDuplicate(String name, String username) {
        User user = userService.selectByUsername(username);
        QueryWrapper<Playlist> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        wrapper.eq("created_by_user_id", user.getId());
        Playlist playlist = playlistMapper.selectOne(wrapper);
        return playlist != null;
    }

    @Override
    public int deletePlaylist(PlaylistSelectRequest request) {
        // 先删关系表，再删歌单，否则会触发SQL外键异常
        userPlaylistService.delete(request.getPlaylistName(), request.getUsername());
        Playlist playlist = selectPlaylist(request);
        if (playlist == null) {
            return 0;
        }
        Integer musicNum = playlist.getMusicNum();
        int delete = playlistMapper.deleteById(playlist.getId());

        int deleteMusicNum = deletePlaylistMusics(playlist.getId());
        if (deleteMusicNum == musicNum) {
            // 删除了全部歌单-歌曲关联
            return delete + 1;
        } else {
            // 未删除完全
            return delete;
        }
    }

    @Override
    public int updatePlaylist(PlaylistUpdateRequest request) {
        Playlist playlist = selectPlaylist(new PlaylistSelectRequest().setPlaylistName(request.getOldName()).setUsername(request.getOldUsername()));
        if (playlist != null) {
            if (request.getNewName() != null && !request.getNewName().equals(playlist.getName())) {
                playlist.setName(request.getNewName());
            }
            if (request.getNewUsername() != null) {
                User user = userService.selectByUsername(request.getNewUsername());
                if (user != null && !user.getId().equals(playlist.getCreatedByUserId())) {
                    playlist.setCreatedByUserId(user.getId());
                }
            }
            if (request.getVisibility() != null && !request.getVisibility().equals(playlist.getVisibility())) {
                playlist.setVisibility(request.getVisibility());
            }
            if (request.getDescription() != null) {
                playlist.setDescription(request.getDescription());
            }
            if (request.getMusicNum() != null) {
                playlist.setMusicNum(request.getMusicNum());
            }
            if (request.getCoverId() != null) {
                playlist.setCoverId(request.getCoverId());
            }
            if (request.getCollectionNum() != null) {
                playlist.setCollectNum(request.getCollectionNum());
            }
            return playlistMapper.updateById(playlist);
        }
        return -1;
    }

    @Override
    public Playlist selectPlaylist(PlaylistSelectRequest request) {
        QueryWrapper<Playlist> wrapper = new QueryWrapper<>();
        wrapper.eq("name", request.getPlaylistName());

        User user = userService.selectByUsername(request.getUsername());
        if (user != null) {
            wrapper.eq("created_by_user_id", user.getId());
            return playlistMapper.selectOne(wrapper);
        }
        return null;
    }

    @Override
    public void addMusicNum(String playlistId, int addNum) {
        if (addNum != 0) {
            Playlist playlist = playlistMapper.selectById(playlistId);
            playlist.setMusicNum(playlist.getMusicNum() + addNum);
            playlistMapper.updateById(playlist);
        }
    }

    @Override
    public List<Music> addMusic2Playlist(PlaylistMusicAddRequest request) {
        List<Music> musicList = musicService.getMusicListByMusicAddInfoList(request.getMusicAddInfoList());

        Playlist playlist = selectPlaylist(new PlaylistSelectRequest()
                .setUsername(request.getUsername()).setPlaylistName(request.getPlaylistName()));

        if (playlist != null) {
            return addMusicList2Playlist(musicList, playlist);
        }
        return null;
    }

    private List<Music> addMusicList2Playlist(List<Music> musicList, Playlist playlist) {
        if (playlist == null) {
            return null;
        }
        String playlistId = playlist.getId();
        // 去重
        duplicateRemoval(musicList);
        List<Music> successList = new ArrayList<>();
        int total = 0, resort = 0;
        for (Music music : musicList) {
            QueryWrapper<PlaylistMusic> wrapper = new QueryWrapper<>();
            wrapper.eq("playlist_id", playlistId).eq("music_id", music.getId());
            PlaylistMusic existMusic = playlistMusicMapper.selectOne(wrapper);
            if (existMusic != null) {
                // 修改updateTime，然后按updateTime顺序查询
                log.warn("音乐调换顺序（删除后重新添加）：" + music.getName());
                existMusic.setUpdatedTime(new Date());
                int update = playlistMusicMapper.update(existMusic, new QueryWrapper<PlaylistMusic>().eq("playlist_id", existMusic.getPlaylistId())
                        .eq("music_id", existMusic.getMusicId()));
                resort += update;
                continue;
            }
            int insert = playlistMusicMapper.insert(new PlaylistMusic().setMusicId(music.getId()).setPlaylistId(playlistId));
            if (insert == 1) {
                successList.add(music);
            }
            total += insert;
        }
        // 修改playlist歌曲数量
        addMusicNum(playlistId, total);
        log.warn("成功插入：" + total + "，成功调整顺序：" + resort);
        return successList;
    }

    @Override
    public List<Playlist> selectPlaylists(String username, Integer relation) {
        QueryWrapper<UserPlaylist> wrapper = new QueryWrapper<>();
        User user = userService.selectByUsername(username);
        String userid = user.getId();
        wrapper.eq("user_id", userid);
        wrapper.eq("relation", relation);
        List<UserPlaylist> userPlaylists = userPlaylistMapper.selectList(wrapper);
        List<String> playlistIds = userPlaylists.stream().map(UserPlaylist::getPlaylistId).collect(Collectors.toList());
        if (playlistIds.size() == 0) {
            return null;
        }
        return playlistMapper.selectBatchIds(playlistIds);
    }

    @Override
    public UserPlaylistsSelectResponse.PlaylistInfo transPlaylist2PlaylistInfo(Playlist playlist) {
        User user = userService.selectById(playlist.getCreatedByUserId());
        String createdUserNickname = null;
        if (user != null) {
            createdUserNickname = user.getNickname();
        }
        return new UserPlaylistsSelectResponse.PlaylistInfo(playlist.getName(),
                playlist.getMusicNum(), playlist.getVisibility(), createdUserNickname, playlist.getDescription());
    }

    @Override
    public List<Music> deleteMusicfromPlaylist(PlaylistMusicAddRequest request) {
        List<Music> musicList = musicService.getMusicListByMusicAddInfoList(request.getMusicAddInfoList());

        Playlist playlist = selectPlaylist(new PlaylistSelectRequest()
                .setUsername(request.getUsername()).setPlaylistName(request.getPlaylistName()));

        List<Music> successList = new ArrayList<>();
        if (playlist != null) {
            String playlistId = playlist.getId();
            // 去重
            duplicateRemoval(musicList);

            int total = 0;
            for (Music music : musicList) {
                QueryWrapper<PlaylistMusic> wrapper = new QueryWrapper<>();
                wrapper.eq("playlist_id", playlistId).eq("music_id", music.getId());
                int delete = playlistMusicMapper.delete(wrapper);
                if (delete == 0) {
                    log.warn("删除音乐失败：" + music.getName());
                    continue;
                }
                successList.add(music);
                total += delete;
            }
            // 修改playlist歌曲数量
            addMusicNum(playlistId, -total);
            log.warn("成功删除：" + total);
            return successList;
        }
        return null;
    }

    @Override
    public List<Music> addMusic2Mylike(PlaylistMusicAddRequest request) {
        List<Music> musicList = musicService.getMusicListByMusicAddInfoList(request.getMusicAddInfoList());
        List<Playlist> playlists = selectPlaylists(request.getUsername(), UserPlaylistRelation.MY_LIKE.getRelation());
        if (playlists != null && playlists.size() == 1) {
            Playlist mylike = playlists.get(0);
            return addMusicList2Playlist(musicList, mylike);
        } else {
            log.error("用户喜欢歌单数量异常！");
            return null;
        }
    }

    @Override
    public List<Music> removeFromMylike(PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getMusicAddInfoList() == null || request.getMusicAddInfoList().size() == 0) {
            return null;
        }
        if (request.getPlaylistName() == null) {
            request.setPlaylistName(request.getUsername() + "的喜欢歌单");
        }
        return deleteMusicfromPlaylist(request);
    }

    @Override
    public List<Music> addMusic2RecentlyPlay(PlaylistMusicAddRequest request) {
        List<Music> musicList = musicService.getMusicListByMusicAddInfoList(request.getMusicAddInfoList());
        List<Playlist> playlists = selectPlaylists(request.getUsername(), UserPlaylistRelation.RECENTLY_PLAY.getRelation());
        if (playlists != null && playlists.size() == 1) {
            Playlist recentlyPlay = playlists.get(0);
            return addMusicList2Playlist(musicList, recentlyPlay);
        } else {
            log.error("用户最近播放歌单数量异常！");
            return null;
        }
    }

    @Override
    public List<Music> removeFromRecentlyPlay(PlaylistMusicAddRequest request) {
        if (request.getUsername() == null || request.getMusicAddInfoList() == null || request.getMusicAddInfoList().size() == 0) {
            return null;
        }
        if (request.getPlaylistName() == null) {
            request.setPlaylistName(request.getUsername() + "的最近播放");
        }
        return deleteMusicfromPlaylist(request);
    }

    @Override
    public List<UserPlaylistsSelectResponse.PlaylistInfo> fuzzySearchPlaylist(String searchContent) {
        QueryWrapper<Playlist> wrapper = new QueryWrapper<>();
        wrapper.like("name", searchContent);
        List<Playlist> playlists = playlistMapper.selectList(wrapper);
        if (playlists != null) {
            List<UserPlaylistsSelectResponse.PlaylistInfo> infoList = new ArrayList<>();
            for (Playlist pl : playlists) {
                infoList.add(transPlaylist2PlaylistInfo(pl));
            }
            return infoList;
        } else {
            return null;
        }
    }

    /**
     * 删除所有与歌单-歌曲关联表信息,返回删除成功条目
     */
    private int deletePlaylistMusics(String playlistId) {
        QueryWrapper<PlaylistMusic> wrapper = new QueryWrapper<>();
        wrapper.eq("playlist_id", playlistId);
        return playlistMusicMapper.delete(wrapper);
    }


    private Playlist createRequest2Entity(PlaylistCreateRequest request) {
        Playlist playlist = new Playlist();
        String name = request.getName();
        String createdUsername = request.getUsername();
        if (name == null || name.length() == 0 || createdUsername == null || createdUsername.length() == 0) {
            return null;
        }
        playlist.setName(name);
        User user = userService.selectByUsername(request.getUsername());
        if (user != null) {
            playlist.setCreatedByUserId(user.getId());
        }
        if (request.getCoverId() != null) {
            playlist.setCoverId(request.getCoverId());
        }
        if (request.getDescription() != null) {
            playlist.setDescription(request.getDescription());
        }
        if (request.getVisibility() != null) {
            playlist.setVisibility(request.getVisibility());
        }
        if (request.getMusicNum() != null) {
            playlist.setMusicNum(request.getMusicNum());
        }
        return playlist;
    }

    private void duplicateRemoval(List<Music> musicList) {
        Set<Music> set = new HashSet<>(musicList);
        musicList = new ArrayList<>();
        musicList.addAll(set);
    }
}
