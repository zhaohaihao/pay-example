package com.zhh.payment.mall.enums;

import lombok.Getter;

/**
 * @author zhh
 * @description
 * @date 2020-03-24 20:15
 */
@Getter
public enum PaymentTypeEnum {

    PAY_ONLINE(1),
    ;

    Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
