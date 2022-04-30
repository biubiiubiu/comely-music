package com.example.comelymusic.generate.controller.requests;

import com.example.comelymusic.generate.enums.PlayerModule;
import lombok.Data;

/**
 * description: 根据音乐类型分页查询音乐列表
 *
 * @author: zhangtian
 * @since: 2022-04-24 19:11
 */
@Data
public class MusicSelectRequest {
    private PlayerModule module;
    private Integer num;
}
