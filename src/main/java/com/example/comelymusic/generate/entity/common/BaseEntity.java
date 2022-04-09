package com.example.comelymusic.generate.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * description: 实体类基类
 *
 * @author: zhangtian
 * @since: 2022-04-09 12:43
 */
@Data
public abstract class BaseEntity<T extends Model<?>> extends Model<T> {
    @ApiModelProperty("创建时间")
    @TableField("created_time")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    @TableField("updated_time")
    private Date updatedTime;

    public BaseEntity() {
        Date date = new Date();
        createdTime = date;
        updatedTime = date;
    }
}
