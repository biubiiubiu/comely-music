package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.EntityTagCreateRequest;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.service.EntityTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author zhangtian
 * @since 2022-05-07
 */
@RestController
@RequestMapping("/generate/entityTag")
public class EntityTagController {

    @Autowired
    private EntityTagService entityTagService;

    /**
     * 新增entity-tag, 除了username都不能为null
     */
    @PostMapping("/create")
    public R createEntityTag(@Validated @RequestBody EntityTagCreateRequest request) {
        if (request.getTagName() == null || request.getTagName().length() == 0 || request.getType() == null
                || request.getEntityName() == null || request.getEntityName().length() == 0) {
            return R.setResult(ResultCode.ENTITY_TAG_ADD_ERROR);
        }
        int result = entityTagService.create(request);
        if (result == 0) {
            return R.setResult(ResultCode.ENTITY_TAG_ADD_ERROR);
        }
        return R.ok();
    }

    /**
     * 批量新增entity-tag, 除了username都不能为null
     */
    @PostMapping("/batch-create")
    public R batchCreateEntityTags(@Validated @RequestBody List<EntityTagCreateRequest> requests) {
        int successTotal = 0;
        for (EntityTagCreateRequest request : requests) {
            if (request.getTagName() == null || request.getTagName().length() == 0 || request.getType() == null
                    || request.getEntityName() == null || request.getEntityName().length() == 0) {
                successTotal += 0;
            }
            successTotal += entityTagService.create(request);
        }
        if (successTotal == 0) {
            return R.setResult(ResultCode.ENTITY_TAG_ADD_ERROR);
        }
        return R.ok();
    }

}

