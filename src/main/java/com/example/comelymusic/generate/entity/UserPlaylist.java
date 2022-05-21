package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.comelymusic.generate.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangtian
 * @since 2022-05-12
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user_playlist")
@ApiModel(value = "UserPlaylist对象", description = "")
public class UserPlaylist extends BaseEntity<UserPlaylist> {

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("歌单id")
    @TableField("playlist_id")
    private String playlistId;

    @ApiModelProperty("用户歌单关系，0-我喜欢，1-创建，2-收藏,3-最近播放")
    @TableField("relation")
    private Integer relation;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
