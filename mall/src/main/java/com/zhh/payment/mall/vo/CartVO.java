package com.zhh.payment.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhh
 * @description 购物车VO
 * @date 2020-03-23 15:21
 */
@Data
public class CartVO {

    private List<CartProductVO> cartProductVOList;

    private Boolean selectedAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}
