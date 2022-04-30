package com.example.comelymusic.generate.controller.requests;

import lombok.Data;

import java.util.List;

/**
 * description: 音乐人创建规则
 *
 * @author: zhangtian
 * @since: 2022-04-30 16:51
 */
@Data
public class ArtistCreateRequest {
    private String artistName;
    private String remark;
    private String photoId;
    private Integer fansNum;
    private String status;
    private Integer recommendFactor;
    private String worksPlaylist_id;
}
