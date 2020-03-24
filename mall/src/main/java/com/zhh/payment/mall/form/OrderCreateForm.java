package com.zhh.payment.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhh
 * @description
 * @date 2020-03-24 22:57
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}
