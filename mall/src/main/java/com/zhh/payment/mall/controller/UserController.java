package com.zhh.payment.mall.controller;

import com.zhh.payment.mall.consts.MallConst;
import com.zhh.payment.mall.form.UserLoginForm;
import com.zhh.payment.mall.form.UserRegisterForm;
import com.zhh.payment.mall.pojo.User;
import com.zhh.payment.mall.service.impl.UserServiceImpl;
import com.zhh.payment.mall.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author zhh
 * @description 用户Controller
 * @date 2020-03-22 18:06
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 用户注册
     *
     * @param userRegisterForm      用户表单信息
     * @return
     */
    @PostMapping("/user/register")
    public ResponseVO<User> register(@Valid @RequestBody UserRegisterForm userRegisterForm) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);

        return userServiceImpl.register(user);
    }

    /**
     * 用户登录
     *
     * @param userLoginForm 用户登录信息
     * @param session
     * @return
     */
    @PostMapping("/user/login")
    public ResponseVO<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  HttpSession session
                                  ) {
        log.info("/user/login sessionId={}", session.getId());

        ResponseVO<User> loginResponseVO = userServiceImpl.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        // 设置Session
        session.setAttribute(MallConst.CURRENT_USER, loginResponseVO.getData());

        return loginResponseVO;
    }

    /**
     * 获取用户信息
     *
     * @param session session保存在内存里, 改进版: token+redis
     * @return
     */
    @GetMapping("/user")
    public ResponseVO<User> userInfo(HttpSession session) {
        log.info("/user sessionId={}", session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return ResponseVO.success(user);
    }

    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseVO<User> logout(HttpSession session) {
        log.info("/user/logout sessionId={}", session.getId());

        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVO.success();
    }
}
