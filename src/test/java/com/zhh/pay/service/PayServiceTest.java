package com.zhh.pay.service;

import com.zhh.pay.PayApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author zhh
 * @description
 * @date 2019-11-21 17:12
 */
public class PayServiceTest extends PayApplicationTests {

    @Autowired
    private PayService payService;

    @Test
    public void create() {
        payService.create("ZHH0001", BigDecimal.valueOf(0.01));
    }
}