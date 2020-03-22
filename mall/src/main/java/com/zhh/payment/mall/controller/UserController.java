package com.zhh.payment.mall.controller;

import com.zhh.payment.mall.form.UserForm;
import com.zhh.payment.mall.pojo.User;
import com.zhh.payment.mall.service.impl.UserServiceImpl;
import com.zhh.payment.mall.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.zhh.payment.mall.enums.ResponseEnum.PARAM_ERROR;

/**
 * @author zhh
 * @description 用户Controller
 * @date 2020-03-22 18:06
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 用户注册
     *
     * @param userForm      用户表单信息
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public ResponseVO register(@Valid @RequestBody UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("注册提交的参数有误, {} {}",
                    bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVO.error(PARAM_ERROR, bindingResult);
        }

        User user = new User();
        BeanUtils.copyProperties(userForm, user);

        return userServiceImpl.register(user);
    }
}
