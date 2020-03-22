package com.zhh.payment.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhh
 * @description 启动项
 * @date 2020-03-19 14:47
 */
@MapperScan(basePackages = "com.zhh.payment.mall.dao")
@SpringBootApplication
public class MallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class, args);
    }
}
