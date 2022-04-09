package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 歌单歌曲表
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("playlist_music")
@ApiModel(value = "Playlist_music对象", description = "歌单歌曲表")
public class PlaylistMusic extends Model<PlaylistMusic> {

    @ApiModelProperty("歌单ID")
    @TableField("playlist_id")
    private String playlistId;

    @ApiModelProperty("歌曲ID")
    @TableField("music_id")
    private String music_id;


    @Override
    public Serializable pkVal() {
        return null;
    }

}
