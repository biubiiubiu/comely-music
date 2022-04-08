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
public class Fans_artist extends Model<Fans_artist> {

    @ApiModelProperty("用户ID（粉丝）")
    @TableField("user_id")
    private String user_id;

    @ApiModelProperty("歌手ID")
    @TableField("artist_id")
    private String artist_id;


    @Override
    public Serializable pkVal() {
        return null;
    }

}