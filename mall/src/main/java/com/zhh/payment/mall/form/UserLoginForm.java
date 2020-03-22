package com.zhh.payment.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhh
 * @description 用户登录表单
 * @date 2020-03-22 21:56
 */
@Data
public class UserLoginForm {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
