package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.example.comelymusic.generate.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Data
@Accessors(chain = true)
@TableName("user")
@ApiModel(value = "User对象", description = "用户表")
public class User extends BaseEntity<User> {

    @ApiModelProperty("用户ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
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
    private String avatarId;

    @ApiModelProperty("用户身份，0-用户，1-管理员，2-歌手")
    @TableField("role")
    private Integer role = 0;

    public User(){
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
                .append(", username=").append(getUsername())
                .append(", nickname=").append(getNickname())
                .append(", gender=").append(getGender())
                .append(", avatar_id=").append(getAvatarId())
                .append(", role=").append(getRole())
                .append(", created_time=").append(getCreatedTime())
                .append(", updated_time=").append(getUpdatedTime())
                .append("]");
        return sb.toString();
    }

}
