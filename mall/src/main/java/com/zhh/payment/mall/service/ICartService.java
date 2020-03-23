package com.zhh.payment.mall.service;

import com.zhh.payment.mall.form.CartAddForm;
import com.zhh.payment.mall.form.CartUpdateForm;
import com.zhh.payment.mall.vo.CartVO;
import com.zhh.payment.mall.vo.ResponseVO;

/**
 * @author zhh
 * @description 购物车Service
 * @date 2020-03-23 15:39
 */
public interface ICartService {

    /**
     * 添加商品
     *
     * @param cartAddForm 购物车添加表单
     * @return
     */
    ResponseVO<CartVO> add(Integer uid, CartAddForm cartAddForm);

    /**
     * 获取购物车商品列表
     *
     * @param uid 用户ID
     * @return
     */
    ResponseVO<CartVO> list(Integer uid);

    /**
     * 更新购物车
     *
     * @param uid            用户ID
     * @param productId      商品ID
     * @param cartUpdateForm 购物车更新表单
     * @return
     */
    ResponseVO<CartVO> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    /**
     * 删除购物车
     *
     * @param uid            用户ID
     * @param productId      商品ID
     * @return
     */
    ResponseVO<CartVO> delete(Integer uid, Integer productId);
}