package com.zhh.payment.mall.enums;

import lombok.Getter;

/**
 * @author zhh
 * @description 角色枚举:0-管理员, 1-普通用户
 * @date 2020-03-22 17:32
 */
@Getter
public enum RoleEnum {

    ADMIN(0),
    CUSTOMER(1),
    ;

    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
