package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.common.ComelyMusicException;
import com.example.comelymusic.generate.common.utils.JwtUtils;
import com.example.comelymusic.generate.common.utils.RedisUtils;
import com.example.comelymusic.generate.controller.requests.user.LoginRequest;
import com.example.comelymusic.generate.controller.requests.user.UserCreateRequest;
import com.example.comelymusic.generate.controller.responses.user.LoginResponse;
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

    @Autowired
    RedisUtils redis;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * 存储到redis的sts-token的key前缀，加上用户名就可以组成key
     */
    private final static String LOGIN_TOKEN_KEY_PREFIX = "login-token-";

    /**
     * 创建新用户
     *
     * @param userCreateRequest 创建约束，userCreateRequest字段不能缺失
     * @return 创建结果
     * @throws ComelyMusicException 用户已经存在的异常
     */
    @Override
    public int create(UserCreateRequest userCreateRequest) throws ComelyMusicException {
        if (checkUserNameExists(userCreateRequest.getUsername())) {
            ComelyMusicException se = new ComelyMusicException(ResultCode.USERNAME_EXISTS);
            log.error(se.toString());
            return 0;
        }
        User user = userCreateRequestToUser(userCreateRequest);
        return userMapper.insert(user);
    }

    /**
     * 根据用户名删除单个用户
     *
     * @param username 用户名
     * @return 删除结果
     * @throws ComelyMusicException 异常
     */
    @Override
    public int deleteByUsername(String username) throws ComelyMusicException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.delete(wrapper);
    }

    /**
     * 根据用户名查询单个用户
     *
     * @param username 用户名
     * @return 查询结果
     * @throws ComelyMusicException 用户不存在异常
     */
    @Override
    public User selectByUsername(String username) throws ComelyMusicException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 修改用户信息
     *
     * @param userCreateRequest 修改的约束，userCreateRequest字段不能缺失
     * @return 修改结果
     * @throws ComelyMusicException 用户不存在的异常
     */
    @Override
    public int update(UserCreateRequest userCreateRequest) throws ComelyMusicException {
        String username = userCreateRequest.getUsername();
        if (!checkUserNameExists(username)) {
            ComelyMusicException se = new ComelyMusicException(ResultCode.USER_NOT_EXIST);
            log.error(se.toString());
            return 0;
        } else {
            User newUser = oldUser2NewUser(selectByUsername(username), userCreateRequest);
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            return userMapper.update(newUser, wrapper);
        }
    }

    /**
     * 登录接口
     *
     * @param request 用户名和密码
     * @return 登录成功返回用户基本信息和token（token有效期3天），失败返回null
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String key = LOGIN_TOKEN_KEY_PREFIX + username;
        LoginResponse value = (LoginResponse) redis.getObject(key, LoginResponse.class);
        if (value != null) {
            redis.setObject(key, value, JwtUtils.LOGIN_EFFECTIVE_TIME);
            return value;
        } else {
            if (checkUserNameExists(username)) {
                User user = checkUsernameAndPassword(username, password);
                if (user != null) {
                    String token = jwtUtils.createToken(username);
                    LoginResponse response = user2LoginResponse(user, token);
                    redis.setObject(key, response, JwtUtils.LOGIN_EFFECTIVE_TIME);
                    return response;
                }
            }
        }
        return null;
    }

    /**
     * 检查用户名密码，成功返回用户信息，失败返回null
     */
    private User checkUsernameAndPassword(String username, String password) {
        User user = selectByUsername(username);
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * 检查用户名是否存在
     */
    private boolean checkUserNameExists(String username) throws ComelyMusicException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        return user != null;
    }

    /**
     * 根据request创建新用户
     */
    private User userCreateRequestToUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(userCreateRequest.getPassword());
        user.setNickname(userCreateRequest.getNickname());
        user.setGender(userCreateRequest.getGender());
        user.setRole(userCreateRequest.getRole());
        return user;
    }

    /**
     * 修改用户信息
     */
    private User oldUser2NewUser(User oldUser, UserCreateRequest userCreateRequest) {
        User newUser = new User();
        newUser.setId(oldUser.getId());
        newUser.setUsername(userCreateRequest.getUsername());
        // todo 让用户输入旧密码，之后才能修改新密码，或者直接加一个修改密码的接口
        newUser.setPassword(userCreateRequest.getPassword());
        newUser.setNickname(userCreateRequest.getNickname());
        newUser.setGender(userCreateRequest.getGender());
        newUser.setRole(userCreateRequest.getRole());
        return newUser;
    }

    private LoginResponse user2LoginResponse(User user, String token) {
        LoginResponse response = new LoginResponse();
        response.setUsername(user.getUsername())
                .setNickname(user.getNickname())
                .setGender(user.getGender())
                .setRole(user.getRole())
                .setAvatarId(user.getAvatarId())
                .setLoginToken(token);
        return response;
    }
}
