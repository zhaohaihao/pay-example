package com.zhh.payment.mall.controller;

import com.zhh.payment.mall.consts.MallConst;
import com.zhh.payment.mall.form.ShippingForm;
import com.zhh.payment.mall.pojo.User;
import com.zhh.payment.mall.service.impl.ShippingServiceImpl;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author zhh
 * @description 收货地址Controller
 * @date 2020-03-24 12:07
 */
@RestController
public class ShippingController {

    @Autowired
    private ShippingServiceImpl shippingServiceImpl;

    @PostMapping("/shippings")
    public ResponseVO add(@Valid @RequestBody ShippingForm form,
                          HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingServiceImpl.add(user.getId(), form);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVO delete(@PathVariable Integer shippingId,
                             HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingServiceImpl.delete(user.getId(), shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVO update(@PathVariable Integer shippingId,
                             @Valid @RequestBody ShippingForm form,
                             HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingServiceImpl.update(user.getId(), shippingId, form);
    }

    @GetMapping("/shippings")
    public ResponseVO list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                           HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingServiceImpl.list(user.getId(), pageNum, pageSize);
    }
}
