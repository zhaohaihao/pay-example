package com.zhh.payment.pay.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.zhh.payment.pay.PayApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author zhh
 * @description
 * @date 2020-03-20 00:16
 */
public class PayServiceTest extends PayApplicationTest {

    @Autowired
    private PayService payService;

    @Test
    public void create() {
        /**
         * BigDecimal.valueOf(0.01) -> 先转String, 然后才会new BigDecimal
         * new BigDecimal(0.01) -> 精度会出问题, 应该用new BigDecimal("0.01")
         */
        payService.create("2003200013", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }
}