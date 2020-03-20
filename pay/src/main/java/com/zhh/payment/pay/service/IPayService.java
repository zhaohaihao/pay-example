package com.zhh.payment.pay.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @author zhh
 * @description 支付Service接口
 * @date 2020-03-20 00:03
 */
public interface IPayService {

    /**
     * 发起支付
     *
     * @param orderId         订单id
     * @param amount          金额
     * @param bestPayTypeEnum 支付类型枚举
     */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知处理
     *
     * @param notifyData 异步通知数据
     */
    String asyncNotify(String notifyData);
}
