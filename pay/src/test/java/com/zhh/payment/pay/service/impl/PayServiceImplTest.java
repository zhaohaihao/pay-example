package com.zhh.payment.pay.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.zhh.payment.pay.PayApplicationTest;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author zhh
 * @description
 * @date 2020-03-20 00:16
 */
public class PayServiceImplTest extends PayApplicationTest {

    @Autowired
    private PayServiceImpl payServiceImpl;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void create() {
        /**
         * BigDecimal.valueOf(0.01) -> 先转String, 然后才会new BigDecimal
         * new BigDecimal(0.01) -> 精度会出问题, 应该用new BigDecimal("0.01")
         */
        payServiceImpl.create("2003200013", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }

    @Test
    public void sendMQMsg() {
        amqpTemplate.convertAndSend("payNotify", "Hello World!");
    }
}