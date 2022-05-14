package com.example.comelymusic.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.comelymusic.generate.common.ComelyMusicException;
import com.example.comelymusic.generate.common.utils.JwtUtils;
import com.example.comelymusic.generate.common.utils.RedisUtils;
import com.example.comelymusic.generate.controller.requests.LoginRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistCreateRequest;
import com.example.comelymusic.generate.controller.requests.PlaylistSelectRequest;
import com.example.comelymusic.generate.controller.requests.UserCreateRequest;
import com.example.comelymusic.generate.controller.responses.LoginResponse;
import com.example.comelymusic.generate.controller.responses.UserInfoResponse;
import com.example.comelymusic.generate.entity.Playlist;
import com.example.comelymusic.generate.entity.User;
import com.example.comelymusic.generate.enums.Gender;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.mapper.UserMapper;
import com.example.comelymusic.generate.service.PlaylistService;
import com.example.comelymusic.generate.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    private static final String DEFAULT_AVATAR_ID = "DEFAULT-AVATAR-ID";
    private static final String DEFAULT_NICKNAME = "新用户";
    private static final String MY_LIKE_PLAYLIST = "我喜欢";

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtils redis;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    @Lazy
    private PlaylistService playlistService;

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
    public UserInfoResponse selectUserInfoByUsername(String username) throws ComelyMusicException {
        User user = selectByUsername(username);
        return user2UserInfoResponse(user);
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

    @Override
    public User selectByUsername(String username) throws ComelyMusicException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public User selectById(String userid) {
        return userMapper.selectById(userid);
    }

    @Override
    public Boolean judgeNewUser(LoginRequest request) {
        return !checkUserNameExists(request.getUsername());
    }

    /**
     * 存在账号就登录，不存在账号就注册新账号，昵称、头像等用缺省值
     *
     * @param request 用户名密码
     * @return 旧帐号或新账号信息（包括用户基本信息和token（token有效期3天）），失败返回null
     */
    @Override
    public LoginResponse loginOrRegister(LoginRequest request) {
        if (checkUserNameExists(request.getUsername())) {
            return login(request);
        }
        return register(request);
    }

    private LoginResponse register(LoginRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        newUser.setGender(Gender.UNKNOWN.getGender());
        newUser.setRole(0);
        newUser.setNickname(DEFAULT_NICKNAME + UUID.randomUUID().toString().substring(0, 8));
        userMapper.insert(newUser);

        String key = LOGIN_TOKEN_KEY_PREFIX + request.getUsername();
        String token = jwtUtils.createToken(request.getUsername());
        LoginResponse response = user2LoginResponse(newUser, token, true);
        redis.setObject(key, response, JwtUtils.LOGIN_EFFECTIVE_TIME);
        return response;
    }

    private LoginResponse login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        User user = checkUsernameAndPassword(username, password);
        if (user != null) {
            String key = LOGIN_TOKEN_KEY_PREFIX + username;
            LoginResponse value = (LoginResponse) redis.getObject(key, LoginResponse.class);
            if (value != null) {
                // 刷新过期时间
                value.setIsNewUser(false);
                redis.setObject(key, value, JwtUtils.LOGIN_EFFECTIVE_TIME);
                return value;
            } else {
                String token = jwtUtils.createToken(username);
                LoginResponse response = user2LoginResponse(user, token, false);
                redis.setObject(key, response, JwtUtils.LOGIN_EFFECTIVE_TIME);
                return response;
            }
        }
        return null;
    }


    /**
     * 退出登录，销毁token
     */
    @Override
    public void logout(String username) {
        String key = LOGIN_TOKEN_KEY_PREFIX + username;
        LoginResponse value = (LoginResponse) redis.getObject(key, LoginResponse.class);
        if (value != null) {
            redis.del(key);
        }
    }

    /**
     * 用户是否登录
     */
    @Override
    public boolean getLoginStatus(String username) {
        String key = LOGIN_TOKEN_KEY_PREFIX + username;
        return redis.get(key) != null;
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

    private String createMyLikePlaylist(String username) {
        PlaylistCreateRequest request = new PlaylistCreateRequest();
        request.setUsername(username).setName(MY_LIKE_PLAYLIST);
        int i = playlistService.create(request);
        if (i != 0) {
            Playlist myLikePlaylist = playlistService.selectPlaylist(
                    new PlaylistSelectRequest().setPlaylistName(MY_LIKE_PLAYLIST).setUsername(username));
            return myLikePlaylist.getId();
        }
        return null;
    }

    /**
     * 修改用户信息
     */
    private User oldUser2NewUser(User oldUser, UserCreateRequest request) {
        User newUser = new User();
        newUser.setId(oldUser.getId());
        if (request.getUsername() != null) {
            newUser.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            newUser.setPassword(request.getPassword());
        }
        if (request.getNickname() != null && request.getNickname().length() > 0) {
            newUser.setNickname(request.getNickname());
        }
        if (request.getGender() != null) {
            newUser.setGender(request.getGender());
        }
        if (request.getRole() != null) {
            newUser.setRole(request.getRole());
        }
        return newUser;
    }

    private LoginResponse user2LoginResponse(User user, String token, boolean isNewUser) {
        LoginResponse response = new LoginResponse();
        response.setUsername(user.getUsername())
                .setNickname(user.getNickname())
                .setGender(user.getGender())
                .setRole(user.getRole())
                .setAvatarId(user.getAvatarId())
                .setLoginToken(token);
        response.setIsNewUser(isNewUser);
        return response;
    }

    private UserInfoResponse user2UserInfoResponse(User user) {
        if (user == null) {
            return null;
        }
        UserInfoResponse response = new UserInfoResponse();
        response.setUsername(user.getUsername())
                .setNickname(user.getNickname())
                .setGender(user.getGender())
                .setRole(user.getRole())
                .setAvatarId(user.getAvatarId());
        return response;
    }
}
