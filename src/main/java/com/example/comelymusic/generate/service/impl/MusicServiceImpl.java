package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.controller.requests.MusicCreateRequest;
import com.example.comelymusic.generate.controller.requests.MusicSelectRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistMusicAddRequest;
import com.example.comelymusic.generate.controller.responses.MusicBatchCreateResponse;
import com.example.comelymusic.generate.controller.responses.MusicSelectResponse;
import com.example.comelymusic.generate.entity.Artist;
import com.example.comelymusic.generate.entity.FileEntity;
import com.example.comelymusic.generate.entity.Music;
import com.example.comelymusic.generate.enums.MusicStatus;
import com.example.comelymusic.generate.enums.PlayerModule;
import com.example.comelymusic.generate.mapper.ArtistMapper;
import com.example.comelymusic.generate.mapper.FileMapper;
import com.example.comelymusic.generate.mapper.MusicMapper;
import com.example.comelymusic.generate.service.ArtistService;
import com.example.comelymusic.generate.service.FileService;
import com.example.comelymusic.generate.service.MusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 歌曲表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Service
@Slf4j
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {
    @Autowired
    private MusicMapper musicMapper;

    @Autowired
    private ArtistMapper artistMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private ArtistService artistService;

    private final static int DEFAULT_NUM = 10;
    private final static String DEFAULT_DESCRIPTION = "暂无描述";

    /**
     * 创建新music，单个，会根据输入的歌名查找到对应的封面、mp3、歌词文件id
     *
     * @param request 约束
     * @return 0失败 1成功
     */
    @Override
    public int create(MusicCreateRequest request) {
        // 防止重复（音乐名+歌手），这里 (name,artist_id)是联合索引
        QueryWrapper<Music> wrapper = new QueryWrapper<>();
        wrapper.eq("name", request.getName());

        Artist artist = artistService.selectByArtistName(request.getArtistName());
        if (artist != null) {
            wrapper.eq("artist_id", artist.getId());
            Music existenceMusic = musicMapper.selectOne(wrapper);
            if (existenceMusic != null) {
                return -1;
            }
        }
        Music music = request2Music(request);
        return musicMapper.insert(music);
    }

    /**
     * 随机查询num条满足播放模式条件的歌曲
     */
    @Override
    public MusicSelectResponse selectByModule(MusicSelectRequest request) {
        int num = request.getNum() == null ? DEFAULT_NUM : request.getNum();
        MusicSelectResponse response = new MusicSelectResponse();
        List<MusicSelectResponse.MusicInfo> responseList = new ArrayList<>();
        PlayerModule module = request.getModule();
        if (PlayerModule.RANDOM != module && PlayerModule.STUDY != module) {
            module = PlayerModule.RANDOM;
        }
        QueryWrapper<Music> wrapper = new QueryWrapper<>();
        wrapper.eq("player_module", module);
        List<Music> musicList = musicMapper.selectList(wrapper);
        responseList = music2MusicResponse(musicList, num);
        response.setMusicList(responseList);
        return response;
    }

    /**
     * 根据歌名模糊搜索歌曲
     */
    @Override
    public MusicSelectResponse fuzzySearch(String name) {
        QueryWrapper<Music> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        List<Music> musicList = musicMapper.selectList(wrapper);
        List<MusicSelectResponse.MusicInfo> musicInfos = music2MusicResponse(musicList, musicList.size());
        MusicSelectResponse response = new MusicSelectResponse();
        response.setMusicList(musicInfos);
        return response;
    }

    @Override
    public List<Music> getMusicListByMusicAddInfoList(List<PlaylistMusicAddRequest.MusicAddInfo> musicAddInfoList) {
        List<Music> result = new ArrayList<>();
        for (PlaylistMusicAddRequest.MusicAddInfo info : musicAddInfoList) {
            Artist artist = artistService.selectByArtistName(info.getArtistName());
            if (artist != null) {
                QueryWrapper<Music> wrapper = new QueryWrapper<>();
                wrapper.eq("name", info.getTitle());
                wrapper.eq("artist_id", artist.getId());
                Music music = musicMapper.selectOne(wrapper);
                result.add(music);
            } else {
                log.error("找不到音乐：" + info.getTitle() + "，歌手：" + info.getArtistName());
            }
        }
        return result;
    }

    @Override
    public MusicBatchCreateResponse batchCreate(List<MusicCreateRequest> requestList) {
        MusicBatchCreateResponse response = new MusicBatchCreateResponse();
        List<String> successList = new ArrayList<>();
        List<String> failedList = new ArrayList<>();
        int successTotal = 0, failedTotal = 0;
        for (MusicCreateRequest request : requestList) {
            int i = create(request);
            if (i == 1) {
                successTotal += 1;
                successList.add(request.getName());
            } else {
                failedList.add(request.getName());
                failedTotal += 1;
            }
        }
        response.setSuccessNum(successTotal).setFailedNum(failedTotal)
                .setSuccessMusicList(successList).setFailedMusicList(failedList);
        return response;
    }

    /**
     * List<Music>经过查询转换成 [最多包含数量num] 的List<MusicSelectResponse.MusicInfo>
     */
    private List<MusicSelectResponse.MusicInfo> music2MusicResponse(List<Music> musicList, int num) {
        if (musicList == null) {
            return null;
        }
        int total = Math.min(musicList.size(), num);
        List<MusicSelectResponse.MusicInfo> responseList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Music music = musicList.get(i);
            Artist artist = artistMapper.selectById(music.getArtistId());
            String artistName = artist == null ? null : artist.getName();
            FileEntity cover = fileMapper.selectById(music.getCoverId());
            FileEntity mp3 = fileMapper.selectById(music.getMp3Id());
            FileEntity lyric = fileMapper.selectById(music.getLyricId());
            String coverPath = cover == null ? null : cover.getStorageUrl();
            String audioPath = mp3 == null ? null : mp3.getStorageUrl();
            String lyricPath = lyric == null ? null : lyric.getStorageUrl();
            MusicSelectResponse.MusicInfo info =
                    new MusicSelectResponse.MusicInfo(music.getName(), artistName, audioPath, coverPath, lyricPath);
            responseList.add(info);
        }
        return responseList;
    }


    private Music request2Music(MusicCreateRequest request) {
        Music music = new Music();
        String name = request.getName();
        music.setName(name);
        music.setDescription(request.getDescription() != null ? request.getDescription() : DEFAULT_DESCRIPTION);
        String artistId = null;
        if (request.getArtistName() != null) {
            Artist artist = artistService.selectByArtistName(request.getArtistName());
            if (artist != null) {
                artistId = artist.getId();
            }
        }
        music.setArtistId(artistId);

        String coverId = request.getCoverId();
        if (coverId == null) {
            coverId = fileService.getIdByFilename(name + ".jpg");
        }

        String mp3Id = request.getMp3Id();
        if (mp3Id == null) {
            mp3Id = fileService.getIdByFilename(name + ".mp3");
        }

        String lyricId = request.getLyricId();
        if (lyricId == null) {
            lyricId = fileService.getIdByFilename(name + ".lrc");
        }

        music.setCoverId(coverId);
        music.setMp3Id(mp3Id);
        music.setLyricId(lyricId);

        String status = request.getStatus();
        if (MusicStatus.CLOSED.toString().equals(status) || MusicStatus.PUBLISHED.toString().equals(status)) {
            music.setStatus(status);
        } else {
            music.setStatus(MusicStatus.PUBLISHED.toString());
        }
        String playerModule = request.getPlayerModule();
        if (PlayerModule.RANDOM.toString().equals(playerModule) || PlayerModule.STUDY.toString().equals(playerModule)) {
            music.setPlayerModule(playerModule);
        } else {
            music.setPlayerModule(PlayerModule.RANDOM.toString());
        }
        return music;
    }
}
