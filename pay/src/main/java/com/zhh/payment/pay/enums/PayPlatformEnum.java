package com.zhh.payment.pay.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Getter;

/**
 * @author zhh
 * @description 支付平台枚举, 1-支付宝, 2-微信
 * @date 2020-03-20 17:55
 */
@Getter
public enum PayPlatformEnum {

    ALIPAY(1), WX(2),
    ;

    Integer code;

    PayPlatformEnum(Integer code) {
        this.code = code;
    }

    /**
     * 根据支付类型获取支付平台
     *
     * @param bestPayTypeEnum 支付类型
     * @return
     */
    public static PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum) {
        for (PayPlatformEnum payPlatformEnum : PayPlatformEnum.values()) {
            if (bestPayTypeEnum.getPlatform().name().equals(payPlatformEnum.name())) {
                return payPlatformEnum;
            }
        }

        throw new RuntimeException("错误的支付平台:" + bestPayTypeEnum.name());
    }
}
