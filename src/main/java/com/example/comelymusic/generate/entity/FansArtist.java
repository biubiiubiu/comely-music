package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.comelymusic.generate.entity.common.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 粉丝歌手关联表
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fans_artist")
@ApiModel(value = "Fans_artist对象", description = "粉丝歌手关联表")
public class FansArtist extends BaseEntity<FansArtist> {

    @ApiModelProperty("用户ID（粉丝）")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("歌手ID")
    @TableField("artist_id")
    private String artistId;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
