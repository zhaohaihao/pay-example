package com.zhh.payment.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhh
 * @description 购物车添加表单
 * @date 2020-03-23 15:30
 */
@Data
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;
}
