package com.example.comelymusic.generate.controller;


import com.example.comelymusic.generate.common.R;
import com.example.comelymusic.generate.controller.createrequest.UserCreateRequest;
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
        return R.ok().data("items", userList).message("用户列表");
    }

    /**
     * 新增用户
     */
    @PostMapping()
    public R create(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        int result = userService.create(userCreateRequest);
        if (result == -1) {
            return R.setResult(ResultCode.USERNAME_EXISTS);
        }
        return R.ok();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/username")
    public R delete(@PathVariable String username) {
        int delete = userService.deleteByUsername(username);
        if (delete == 0) {
            R.setResult(ResultCode.USER_NOT_EXIST);
        }
        return R.ok();
    }
}

