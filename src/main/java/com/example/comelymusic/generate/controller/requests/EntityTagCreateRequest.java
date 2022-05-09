package com.example.comelymusic.generate.controller.requests;

import com.example.comelymusic.generate.enums.TagType;
import lombok.Data;

/**
 * description: 音乐标签添加约束
 *
 * @author: zhangtian
 * @since: 2022-05-09 15:37
 */
@Data
public class EntityTagCreateRequest {
    /**
     * 标签，由用户创建
     */
    private String tagName;
    /**
     * 实体类型
     */
    private TagType type;
    /**
     * 实体名称
     */
    private String entityName;
    /**
     * 歌单的用户名
     */
    private String username;
}
