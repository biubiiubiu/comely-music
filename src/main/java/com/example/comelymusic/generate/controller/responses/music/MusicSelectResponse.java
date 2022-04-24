package com.example.comelymusic.generate.controller.responses.music;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * description: 查询音乐接口
 *
 * @author: zhangtian
 * @since: 2022-04-24 18:52
 */
@Data
public class MusicSelectResponse {
    List<MusicInfo> musicList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MusicInfo {
        private String name;
        private String artistName;
        /**
         * 在oss上存储的位置
         */
        private String audioStoragePath;
        private String coverStoragePath;
        private String lyricStoragePath;
    }
}
