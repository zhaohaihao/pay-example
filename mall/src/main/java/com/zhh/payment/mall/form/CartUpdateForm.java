package com.zhh.payment.mall.form;

import lombok.Data;

/**
 * @author zhh
 * @description 购物车更新表单
 * @date 2020-03-23 19:33
 */
@Data
public class CartUpdateForm {

    private Integer quantity;

    private Boolean selected;
}
