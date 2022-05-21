package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.controller.requests.EntityTagCreateRequest;
import com.example.comelymusic.generate.entity.EntityTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-05-07
 */
public interface EntityTagService extends IService<EntityTag> {

    int create(EntityTagCreateRequest request);

    /**
     * 根据标签查询musicId 列表
     */
    List<String> selectEntityIdsByTag(String tag);
}
