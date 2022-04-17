package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.example.comelymusic.generate.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("file")
@ApiModel(value = "File对象", description = "文件表")
@AllArgsConstructor
public class FileEntity extends BaseEntity<FileEntity> {

    @ApiModelProperty("文件ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("文件名")
    @TableField("name")
    private String name;

    @ApiModelProperty("文件的唯一uuid值，在oss中取代文件名")
    @TableField("file_key")
    private String fileKey;

    @ApiModelProperty("文件大小；单位byte")
    @TableField("size")
    private Long size;

    @ApiModelProperty("文件类型，MP3-音频，IMAGE-图片，LYRIC-歌词")
    @TableField("type")
    private String type;

    @ApiModelProperty("文件在OSS中存储位置，用于oss增删改查")
    @TableField("storage_url")
    private String storageUrl;

    @ApiModelProperty("文件公网访问url,例：https：//ossxxxhuhehaote/uuid.jpg")
    @TableField("visit_url")
    private String visitUrl;

    public FileEntity() {
        super();
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
