package com.zhh.payment.mall.controller;

import com.zhh.payment.mall.consts.MallConst;
import com.zhh.payment.mall.form.CartAddForm;
import com.zhh.payment.mall.form.CartUpdateForm;
import com.zhh.payment.mall.pojo.User;
import com.zhh.payment.mall.service.impl.CartServiceImpl;
import com.zhh.payment.mall.vo.CartVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author zhh
 * @description 购物车Controller
 * @date 2020-03-23 15:31
 */
@RestController
public class CartController {

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @GetMapping("/carts")
    public ResponseVO<CartVO> list(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartServiceImpl.list(user.getId());
    }

    /**
     * 添加商品
     *
     * @param cartAddForm 购物车添加表单
     * @return
     */
    @PostMapping("/carts")
    public ResponseVO<CartVO> add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartServiceImpl.add(user.getId(), cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVO<CartVO> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm form,
                                     HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartServiceImpl.update(user.getId(), productId, form);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVO<CartVO> delete(@PathVariable Integer productId,
                                     HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartServiceImpl.delete(user.getId(), productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVO<CartVO> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartServiceImpl.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVO<CartVO> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartServiceImpl.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVO<Integer> sum(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartServiceImpl.sum(user.getId());
    }
}
