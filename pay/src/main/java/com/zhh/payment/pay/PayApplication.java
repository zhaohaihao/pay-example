package com.zhh.payment.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhh
 * @description 支付启动项
 * @date 2020-03-20 00:02
 */
@MapperScan(basePackages = "com.zhh.payment.pay.dao")
@SpringBootApplication
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
