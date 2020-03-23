package com.zhh.payment.mall.controller;

import com.zhh.payment.mall.form.CartAddForm;
import com.zhh.payment.mall.service.impl.CartServiceImpl;
import com.zhh.payment.mall.vo.CartVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author zhh
 * @description
 * @date 2020-03-23 15:31
 */
@RestController
public class CartController {

    @Autowired
    private CartServiceImpl cartServiceImpl;

    /**
     * 添加商品
     *
     * @param cartAddForm 购物车添加表单
     * @return
     */
    @PostMapping("/carts")
    @ResponseBody
    public ResponseVO<CartVO> add(@Valid @RequestBody CartAddForm cartAddForm) {
        return null;
    }
}
