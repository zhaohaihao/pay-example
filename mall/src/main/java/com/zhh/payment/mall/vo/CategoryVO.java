package com.zhh.payment.mall.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zhh
 * @description 商品类目VO
 * @date 2020-03-22 23:43
 */
@Data
public class CategoryVO {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;

    private List<CategoryVO> subCategories;
}
