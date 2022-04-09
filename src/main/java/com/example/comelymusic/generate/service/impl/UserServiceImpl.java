package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.common.ServiceException;
import com.example.comelymusic.generate.controller.createrequest.UserCreateRequest;
import com.example.comelymusic.generate.entity.User;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.mapper.UserMapper;
import com.example.comelymusic.generate.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public int create(UserCreateRequest userCreateRequest) throws ServiceException {
        try {
            checkUserName(userCreateRequest.getUsername());
        } catch (ServiceException se) {
            log.error(se.toString());
            return -1;
        }
        User user = userCreateRequestToUser(userCreateRequest);
        return userMapper.insert(user);
    }

    @Override
    public int deleteByUsername(String username) throws ServiceException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.delete(wrapper);
    }

    private void checkUserName(String username) throws ServiceException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        if (user != null) {
            throw new ServiceException(ResultCode.USERNAME_EXISTS);
        }
    }

    private User userCreateRequestToUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword());
        user.setNickname(userCreateRequest.getNickname());
        user.setGender(userCreateRequest.getGender());
        return user;
    }
}
