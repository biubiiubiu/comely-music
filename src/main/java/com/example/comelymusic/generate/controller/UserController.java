package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.requests.UserCreateRequest;
import com.example.comelymusic.generate.entity.User;
import com.example.comelymusic.generate.enums.ResultCode;
import com.example.comelymusic.generate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhangtian
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/generate/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 查询所有user表中的数据
     */
    @GetMapping("/list")
    public R list() {
        //调用service的方法查询
        List<User> userList = userService.list(null);
        return R.ok().data(userList).message("用户列表");
    }

    /**
     * 新增用户
     */
    @PostMapping("/create")
    public R create(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        int result = userService.create(userCreateRequest);
        if (result == 0) {
            return R.setResult(ResultCode.USERNAME_EXISTS);
        }
        return R.ok();
    }

    /**
     * 删除用户
     */
    @PutMapping("/delete/{username}")
    public R delete(@PathVariable("username") String username) {
        int delete = userService.deleteByUsername(username);
        if (delete == 0) {
            R.setResult(ResultCode.USER_NOT_EXIST);
        }
        return R.ok();
    }

    /**
     * 修改用户信息，username不能为null
     */
    @PutMapping("/update")
    public R update(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        int result = userService.update(userCreateRequest);
        if (result == 0) {
            return R.setResult(ResultCode.USER_NOT_EXIST);
        }
        return R.ok();
    }
}

