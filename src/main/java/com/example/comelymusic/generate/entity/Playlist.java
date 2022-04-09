package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.example.comelymusic.generate.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 歌单表
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("playlist")
@ApiModel(value = "Playlist对象", description = "歌单表")
public class Playlist extends BaseEntity<Playlist> {

    @ApiModelProperty("歌单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("歌单名字")
    @TableField("name")
    private String name;

    @ApiModelProperty("歌曲数量")
    @TableField("music_num")
    private Integer musicNum;

    @ApiModelProperty("收藏数量")
    @TableField("collect_num")
    private Integer collectNum;

    @ApiModelProperty("歌单简介")
    @TableField("description")
    private String description;

    @ApiModelProperty("歌单上架状态，DRAFT-草稿，PUBLISHED-已上架，CLOSED-已下架")
    @TableField("status")
    private String status;

    @ApiModelProperty("歌单封面ID")
    @TableField("cover_id")
    private String coverId;

    @ApiModelProperty("创建者用户ID")
    @TableField("created_by_user_id")
    private String createdByUserId;

    @ApiModelProperty("推荐因数：越高越在上面")
    @TableField("recommend_factor")
    private Integer recommendFactor;

    public Playlist() {
        super();
    }


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
