package com.zhh.payment.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhh
 * @description 用户表单
 * @date 2020-03-22 21:56
 */
@Data
public class UserForm {

    // @NotBlank 用于String 判断空格
    // @NotEmpty 用于集合
    // @NotNull  判断不为null

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;
}
