package com.zhh.payment.mall.listener;

import com.google.gson.Gson;
import com.zhh.payment.mall.pojo.PayInfo;
import com.zhh.payment.mall.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhh
 * @description 支付消息监听器
 * @date 2020-03-24 23:44
 */
@Slf4j
@Component
@RabbitListener(queues = "payNotify")
public class PayMsgListener {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    private static final Gson GSON = new Gson();

    @RabbitHandler
    public void process(String msg) {
        log.info("【接收到消息】=> {}", msg);
        // 关于PayInfo, 正确姿势: pay项目提供client.jar, mall项目应用jar
        PayInfo payInfo = GSON.fromJson(msg, PayInfo.class);
        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
            // 修改订单里的状态
            orderServiceImpl.paid(payInfo.getOrderNo());
        }
    }
}
