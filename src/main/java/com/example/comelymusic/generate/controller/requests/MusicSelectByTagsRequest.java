package com.example.comelymusic.generate.controller.requests;

import com.example.comelymusic.generate.enums.PlayerModule;
import lombok.Data;

import java.util.List;

/**
 * description: 根据音乐类型分页查询音乐列表
 *
 * @author: zhangtian
 * @since: 2022-04-24 19:11
 */
@Data
public class MusicSelectByTagsRequest {
    private String username;
    private List<String> tags;
    private Integer num;
}
