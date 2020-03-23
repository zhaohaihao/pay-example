package com.zhh.payment.mall.enums;

import lombok.Getter;

/**
 * @author zhh
 * @description 商品状态枚举: 1-在售 2-下架 3-删除
 * @date 2020-03-23 14:56
 */
@Getter
public enum ProductStatusEnum {

    ON_SALE(1),

    OFF_SALE(2),

    DELETE(3),
    ;

    Integer code;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
