package com.example.comelymusic.generate.controller.requests;

import lombok.Data;

/**
 * description: 音乐上传数据约束
 *
 * @author: zhangtian
 * @since: 2022-04-23 21:15
 */
@Data
public class MusicCreateRequest {
    private String name;
    private String description;
    private String artistName;
    private String coverId;
    private String mp3Id;
    private String lyricId;
    private String status;
    private String playerModule;
}
