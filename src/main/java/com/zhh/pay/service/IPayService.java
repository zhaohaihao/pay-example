package com.zhh.pay.service;

import java.math.BigDecimal;

/**
 * @author zhh
 * @description 支付Service
 * @date 2019-11-21 16:50
 */
public interface IPayService {

    /**
     * 发起支付
     *
     * @param orderId 订单ID
     * @param amount  支付金额
     */
    void create(String orderId, BigDecimal amount);
}
