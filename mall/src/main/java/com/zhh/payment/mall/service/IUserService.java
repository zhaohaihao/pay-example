package com.zhh.payment.mall.service;

import com.zhh.payment.mall.pojo.User;
import com.zhh.payment.mall.vo.ResponseVO;

/**
 * @author zhh
 * @description 用户Service接口
 * @date 2020-03-22 16:59
 */
public interface IUserService {

    /**
     * 注册
     *
     * @param user 用户信息
     */
    ResponseVO register(User user);

    /**
     * 登录
     */
}
