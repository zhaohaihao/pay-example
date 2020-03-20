package com.zhh.payment.pay.service.impl;

import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.zhh.payment.pay.dao.PayInfoMapper;
import com.zhh.payment.pay.enums.PayPlatformEnum;
import com.zhh.payment.pay.pojo.PayInfo;
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

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Override
    public PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum) {
        // 订单信息写入数据库
        PayInfo payInfo = new PayInfo(Long.parseLong(orderId),
                PayPlatformEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode(),
                OrderStatusEnum.NOTPAY.name(),
                amount);
        payInfoMapper.insertSelective(payInfo);

        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("1013588-黑豪的订单1");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(bestPayTypeEnum);

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("发起支付payResponse={}", payResponse);

        return payResponse;
    }

    @Override
    public String asyncNotify(String notifyData) {
        // 签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("异步通知payResponse={}", payResponse);

        // 从数据库中查指定订单号的订单
        // 这种情况比较严重(一般正常情况下是不会发生的), 建议发出告警: 钉钉、短信
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(payResponse.getOrderId()));
        if (payInfo == null) {
            throw new RuntimeException("通过orderNo查询到的结果是null");
        }

        // 如果订单状态不是已支付状态
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())) {
            // 金额校验, double类型比较大小, 精度问题不好控制
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0) {
                throw new RuntimeException("异步通知中的金额和数据库中的金额不一致, orderNo=" + payResponse.getOrderId());
            }

            // 金额如果一致则修改订单支付状态
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            payInfo.setPlatformNumber(payResponse.getOutTradeNo());
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }

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
