package com.example.comelymusic.generate.service.impl;

import com.example.comelymusic.generate.entity.User;
import com.example.comelymusic.generate.mapper.UserDao;
import com.example.comelymusic.generate.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImp extends ServiceImpl<UserDao, User> implements UserService {

}
