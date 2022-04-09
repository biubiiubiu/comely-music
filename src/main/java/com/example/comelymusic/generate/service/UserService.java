package com.example.comelymusic.generate.service;

import com.example.comelymusic.generate.common.ServiceException;
import com.example.comelymusic.generate.controller.createrequest.UserCreateRequest;
import com.example.comelymusic.generate.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
public interface UserService extends IService<User> {
    int create(UserCreateRequest userCreateRequest) throws ServiceException;
    int deleteByUsername(String username) throws ServiceException;
}
