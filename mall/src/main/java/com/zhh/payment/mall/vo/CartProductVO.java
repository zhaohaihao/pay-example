package com.zhh.payment.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhh
 * @description 购物车商品VO
 * @date 2020-03-23 15:24
 */
@Data
public class CartProductVO {

    private Integer productId;

    /**
     * 购买的数量
     */
    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    /**
     * 总价, 等于 quantity * productPrice
     */
    private BigDecimal productTotalPrice;

    private Integer productStock;

    /**
     * 商品是否选中
     */
    private Boolean productSelected;

    public CartProductVO(
            Integer productId,
            Integer quantity,
            String productName,
            String productSubtitle,
            String productMainImage,
            BigDecimal productPrice,
            Integer productStatus,
            BigDecimal productTotalPrice,
            Integer productStock,
            Boolean productSelected
    ) {
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.productSubtitle = productSubtitle;
        this.productMainImage = productMainImage;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productTotalPrice = productTotalPrice;
        this.productStock = productStock;
        this.productSelected = productSelected;
    }
}
