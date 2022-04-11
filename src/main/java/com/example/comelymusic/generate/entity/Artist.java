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
 * 歌手表
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("artist")
@ApiModel(value = "Artist对象", description = "歌手表")
public class Artist extends BaseEntity<Artist> {

    @ApiModelProperty("歌手ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("歌手名字")
    @TableField("name")
    private String name;

    @ApiModelProperty("歌手备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("歌手照片ID")
    @TableField("photo_id")
    private String photoId;

    @ApiModelProperty("粉丝数")
    @TableField("fans_num")
    private Integer fansNum;

    @ApiModelProperty("歌手上架状态，DRAFT-草稿，PUBLISHED-已上架，BLOCKED-已封禁")
    @TableField("status")
    private String status;

    @ApiModelProperty("推荐因数：越高越在上面")
    @TableField("recommend_factor")
    private Integer recommendFactor;

    @ApiModelProperty("作品歌单id，唯一")
    @TableField("works_playlist_id")
    private String worksPlaylistId;

    public Artist(){
        super();
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id=").append(getId())
                .append(", name=").append(getName())
                .append(", remark=").append(getRemark())
                .append(", status=").append(getStatus())
                .append(", photo_id=").append(getPhotoId())
                .append(", fans_num=").append(getFansNum())
                .append(", recommend_factor=").append(getRecommendFactor())
                .append(", works_playlist_id=").append(getWorksPlaylistId())
                .append(", created_time=").append(getCreatedTime())
                .append(", updated_time=").append(getUpdatedTime())
                .append("]");
        return sb.toString();
    }

}
