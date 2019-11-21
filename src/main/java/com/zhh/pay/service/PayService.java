package com.zhh.pay.service;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author zhh
 * @description 支付Service实现
 * @date 2019-11-21 16:53
 */
@Slf4j
@Service
public class PayService implements IPayService {

    @Override
    public void create(String orderId, BigDecimal amount) {
        // 设置配置
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId("wxd898fcb01713c658");
        wxPayConfig.setMchId("1483469312");
        wxPayConfig.setMchKey("098F6BCD4621D373CADE4E832627B4F6");
        wxPayConfig.setNotifyUrl("http://localhost");

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);

        // 发起支付
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("1013588-支付测试订单");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("payResponse={}", payResponse);
    }
}
