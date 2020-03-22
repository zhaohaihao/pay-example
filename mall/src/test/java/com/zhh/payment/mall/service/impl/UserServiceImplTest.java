package com.zhh.payment.mall.service.impl;

import com.zhh.payment.mall.MallApplicationTest;
import com.zhh.payment.mall.enums.ResponseEnum;
import com.zhh.payment.mall.enums.RoleEnum;
import com.zhh.payment.mall.pojo.User;
import com.zhh.payment.mall.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhh
 * @description
 * @date 2020-03-22 17:27
 */
@Transactional
public class UserServiceImplTest extends MallApplicationTest {

    public static final String USERNAME = "黑豪1号";

    public static final String PASSWORD = "123456";

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Before
    public void register() {
        User user = new User(USERNAME, PASSWORD, "heihao1@163.com", RoleEnum.CUSTOMER.getCode());
        userServiceImpl.register(user);
    }

    @Test
    public void login() {
        ResponseVO<User> userResponseVO = userServiceImpl.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), userResponseVO.getStatus());
    }
}