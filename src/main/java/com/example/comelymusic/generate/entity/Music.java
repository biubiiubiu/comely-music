package com.example.comelymusic.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

import com.example.comelymusic.generate.entity.common.BaseEntity;
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
public class Music extends BaseEntity<Music> {

    @ApiModelProperty("歌曲ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("歌曲名")
    @TableField("name")
    private String name;

    @ApiModelProperty("歌曲简介")
    @TableField("description")
    private String description;

    @ApiModelProperty("歌手ID")
    @TableField("artist_id")
    private String artistId;

    @ApiModelProperty("歌曲封面ID")
    @TableField("cover_id")
    private String coverId;

    @ApiModelProperty("mp3文件ID")
    @TableField("mp3_id")
    private String mp3Id;

    @ApiModelProperty("歌词文件ID")
    @TableField("lyric_id")
    private String lyricId;

    @ApiModelProperty("歌曲上架状态，PUBLISHED-已上架，CLOSED-已下架")
    @TableField("status")
    private String status;

    @ApiModelProperty("歌曲适合的播放模式，RANDOM-随机，STUDY-学习模式等")
    @TableField("player_module")
    private String playerModule;

    public Music() {
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
                .append(", name=").append(getName())
                .append(", description=").append(getDescription())
                .append(", artist_id=").append(getArtistId())
                .append(", cover_id=").append(getCoverId())
                .append(", mp3_id=").append(getMp3Id())
                .append(", lyric_id=").append(getLyricId())
                .append(", status=").append(getStatus())
                .append(", created_time=").append(getCreatedTime())
                .append(", updated_time=").append(getUpdatedTime())
                .append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Music music = (Music) o;
        return Objects.equals(id, music.id) && Objects.equals(name, music.name) && Objects.equals(description, music.description) && Objects.equals(artistId, music.artistId) && Objects.equals(coverId, music.coverId) && Objects.equals(mp3Id, music.mp3Id) && Objects.equals(lyricId, music.lyricId) && Objects.equals(status, music.status) && Objects.equals(playerModule, music.playerModule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, artistId);
    }
}
