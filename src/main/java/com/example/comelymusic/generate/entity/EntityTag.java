package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.comelymusic.generate.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author zhangtian
 * @since 2022-05-07
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("entity_tag")
@ApiModel(value = "EntityTag对象", description = "文件表")
public class EntityTag extends BaseEntity<EntityTag> {

    @ApiModelProperty("标签ID")
      @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("实体id，例：music_id playlist_id")
    @TableField("entity_id")
    private String entityId;

    @ApiModelProperty("标签名称，例：古风")
    @TableField("tag_name")
    private String tagName;

    @ApiModelProperty("实体类型，MUSIC PLAYLIST ARTIST")
    @TableField("entity_type")
    private String entityType;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
