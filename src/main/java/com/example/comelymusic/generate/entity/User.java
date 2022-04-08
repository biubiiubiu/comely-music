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
 * 用户表
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user")
@ApiModel(value = "User对象", description = "用户表")
public class User extends Model<User> {

    @ApiModelProperty("用户ID")
      @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("用户昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("加密后的密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty("头像文件ID")
    @TableField("avatar_id")
    private String avatar_id;

    @ApiModelProperty("用户身份，0-用户，1-管理员，2-歌手")
    @TableField("role")
    private Integer role;

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
