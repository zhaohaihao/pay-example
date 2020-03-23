package com.zhh.payment.mall.pojo;

import lombok.Data;

/**
 * @author zhh
 * @description 购物车
 * @date 2020-03-23 16:15
 */
@Data
public class Cart {

    private Integer productId;

    private Integer quantity;

    private Boolean productSelected;

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}
