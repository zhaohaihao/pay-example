package com.zhh.payment.pay.controller;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.zhh.payment.pay.config.WxAccountConfig;
import com.zhh.payment.pay.pojo.PayInfo;
import com.zhh.payment.pay.service.impl.PayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhh
 * @description 支付Controller
 * @date 2020-03-20 10:53
 */
@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayServiceImpl payServiceImpl;

    @Autowired
    private WxAccountConfig wxAccountConfig;

    /**
     * 发起支付
     *
     * @param orderId 订单id
     * @param amount  金额
     * @return
     */
    @GetMapping("/create")
    public ModelAndView create(
            @RequestParam("orderId") String orderId,
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("payType") BestPayTypeEnum bestPayTypeEnum
    ) {
        PayResponse payResponse = payServiceImpl.create(orderId, amount, bestPayTypeEnum);

        Map<String, Object> map = new HashMap<>(2);
        // 支付方式不同, 渲染就不同
        // 微信Native支付使用codeUrl
        // 支付宝PC使用body
        if (bestPayTypeEnum == BestPayTypeEnum.WXPAY_NATIVE) {
            map.put("codeUrl", payResponse.getCodeUrl());
            map.put("orderId", orderId);
            map.put("returnUrl", wxAccountConfig.getReturnUrl());
            return new ModelAndView("create_wx_native", map);
        } else if (bestPayTypeEnum == BestPayTypeEnum.ALIPAY_PC) {
            map.put("body", payResponse.getBody());
            return new ModelAndView("create_alipay_pc", map);
        }
        throw new RuntimeException("暂不支持的支付类型");
    }

    /**
     * 异步通知处理
     *
     * @param notifyData 异步通知数据
     */
    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData) {
        return payServiceImpl.asyncNotify(notifyData);
    }

    /**
     * 根据订单号查询订单信息
     *
     * @param orderId 订单号
     * @return
     */
    @GetMapping("/queryByOrderId")
    @ResponseBody
    public PayInfo queryByOrderId(@RequestParam("orderId") String orderId) {
        log.info("查询订单号为{}的支付记录", orderId);
        return payServiceImpl.queryByOrderId(orderId);
    }
}
