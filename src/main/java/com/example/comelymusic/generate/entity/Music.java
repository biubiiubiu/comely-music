package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 歌曲表
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("music")
@ApiModel(value = "Music对象", description = "歌曲表")
public class Music extends Model<Music> {

    @ApiModelProperty("歌曲ID")
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("歌曲名")
    @TableField("name")
    private String name;

    @ApiModelProperty("歌曲简介")
    @TableField("description")
    private String description;

    @ApiModelProperty("歌手ID")
    @TableField("artist_id")
    private String artist_id;

    @ApiModelProperty("歌曲封面ID")
    @TableField("cover_id")
    private String cover_id;

    @ApiModelProperty("mp3文件ID")
    @TableField("mp3_id")
    private String mp3_id;

    @ApiModelProperty("歌词文件ID")
    @TableField("lyric_id")
    private String lyric_id;

    @ApiModelProperty("歌曲上架状态，PUBLISHED-已上架，CLOSED-已下架")
    @TableField("status")
    private String status;

    @ApiModelProperty("创建时间")
    @TableField("created_time")
    private LocalDateTime created_time;

    @ApiModelProperty("更新时间")
    @TableField("updated_time")
    private LocalDateTime updated_time;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
