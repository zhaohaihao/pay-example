package com.zhh.payment.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhh.payment.mall.MallApplicationTest;
import com.zhh.payment.mall.form.CartAddForm;
import com.zhh.payment.mall.form.CartUpdateForm;
import com.zhh.payment.mall.vo.CartVO;
import com.zhh.payment.mall.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhh
 * @description
 * @date 2020-03-23 16:21
 */
@Slf4j
public class CartServiceImplTest extends MallApplicationTest {

    private Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @Test
    public void add() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(1);
        cartAddForm.setSelected(true);
        cartServiceImpl.add(1, cartAddForm);
    }

    @Test
    public void list() {
        ResponseVO<CartVO> list = cartServiceImpl.list(1);
        log.info("list={}", GSON.toJson(list));
    }

    @Test
    public void update() {
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(10);
        ResponseVO<CartVO> list = cartServiceImpl.update(1, 1, cartUpdateForm);
        log.info("list={}", GSON.toJson(list));
    }

    @Test
    public void delete() {
        ResponseVO<CartVO> list = cartServiceImpl.delete(1, 1);
        log.info("list={}", GSON.toJson(list));
    }

    @Test
    public void selectAll() {
        ResponseVO<CartVO> list = cartServiceImpl.selectAll(1);
        log.info("list={}", GSON.toJson(list));
    }

    @Test
    public void unSelectAll() {
        ResponseVO<CartVO> list = cartServiceImpl.unSelectAll(1);
        log.info("list={}", GSON.toJson(list));
    }

    @Test
    public void sum() {
        ResponseVO<Integer> list = cartServiceImpl.sum(1);
        log.info("list={}", GSON.toJson(list));
    }
}