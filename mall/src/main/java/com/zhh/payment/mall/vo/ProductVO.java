package com.zhh.payment.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhh
 * @description 商品VO
 * @date 2020-03-23 12:35
 */
@Data
public class ProductVO {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
