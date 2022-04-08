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
public class Artist extends Model<Artist> {

    @ApiModelProperty("歌手ID")
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("歌手名字")
    @TableField("name")
    private String name;

    @ApiModelProperty("歌手备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("歌手照片ID")
    @TableField("photo_id")
    private String photo_id;

    @ApiModelProperty("粉丝数")
    @TableField("fans_num")
    private Integer fans_num;

    @ApiModelProperty("歌手上架状态，DRAFT-草稿，PUBLISHED-已上架，BLOCKED-已封禁")
    @TableField("status")
    private String status;

    @ApiModelProperty("推荐因数：越高越在上面")
    @TableField("recommend_factor")
    private Integer recommend_factor;

    @ApiModelProperty("作品歌单id，唯一")
    @TableField("works_playlist_id")
    private String works_playlist_id;

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
