package com.zhh.payment.mall.service.impl;

import com.zhh.payment.mall.MallApplicationTest;
import com.zhh.payment.mall.enums.RoleEnum;
import com.zhh.payment.mall.pojo.User;
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

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    public void register() {
        User user = new User("黑豪1号", "123456", "heihao1@163.com", RoleEnum.CUSTOMER.getCode());
        userServiceImpl.register(user);
    }
}