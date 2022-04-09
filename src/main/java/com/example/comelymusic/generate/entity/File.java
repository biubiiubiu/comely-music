package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.example.comelymusic.generate.entity.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
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
public class File extends BaseEntity<File> {

    @ApiModelProperty("文件ID")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("文件名")
    @TableField("name")
    private String name;

    @ApiModelProperty("文件hash值，即ObjectKey")
    @TableField("file_key")
    private String fileKey;

    @ApiModelProperty("文件后缀名")
    @TableField("ext")
    private String ext;

    @ApiModelProperty("文件大小；单位byte")
    @TableField("size")
    private Integer size;

    @ApiModelProperty("文件类型，AUDIO-音频，IMAGE-图片，VIDEO-视频，LYRIC-歌词，OTHER-其他")
    @TableField("type")
    private String type;

    @ApiModelProperty("存储供应商，COS-腾讯云存储，OSS-阿里云存储")
    @TableField("storage")
    private String storage;

    @ApiModelProperty("文件状态，UPLOADING-上传中，UPLOADED-已上传，CANCEL-已取消")
    @TableField("status")
    private String status;

    public File() {
        super();
    }


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
