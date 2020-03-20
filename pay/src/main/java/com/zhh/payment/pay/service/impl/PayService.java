package com.zhh.payment.pay.service.impl;

import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.zhh.payment.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author zhh
 * @description 支付Service实现类
 * @date 2020-03-20 00:04
 */
@Slf4j
@Service
public class PayService implements IPayService {

    @Autowired
    private BestPayService bestPayService;

    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
        // 订单信息写入数据库
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("1013588-黑豪的订单1");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(bestPayTypeEnum);

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("payResponse={}", payResponse);

        return payResponse;
    }

    @Override
    public String asyncNotify(String notifyData) {
        // 签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("payResponse={}", payResponse);
        // 金额校验 (从数据库中查订单)

        // 修改订单支付状态

        if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX) {
            // 告诉微信不要再通知了
            return "<xml>\n" + "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
        } else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY) {
            return "success";
        }

        throw new RuntimeException("异步通知中错误的支付平台");
    }
}
