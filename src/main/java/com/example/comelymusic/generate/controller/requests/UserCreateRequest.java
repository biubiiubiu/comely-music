package com.example.comelymusic.generate.controller.requests;

import com.example.comelymusic.generate.enums.Gender;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * description: 用户创建规范
 *
 * @author: zhangtian
 * @since: 2022-04-08 22:21
 */
@Data
public class UserCreateRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 64, message = "用户名长度应该在4个字符到64个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 64, message = "密码长度应该在4个字符到64个字符之间")
    private String password;

    private String nickname;

    private String gender = Gender.UNKNOWN.getGender();

    // "用户身份，0-用户，1-管理员，2-歌手"
    private Integer role = 0;

}
