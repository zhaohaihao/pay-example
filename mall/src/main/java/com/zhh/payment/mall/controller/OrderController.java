package com.zhh.payment.mall.controller;

import com.github.pagehelper.PageInfo;
import com.zhh.payment.mall.consts.MallConst;
import com.zhh.payment.mall.form.OrderCreateForm;
import com.zhh.payment.mall.pojo.User;
import com.zhh.payment.mall.service.impl.OrderServiceImpl;
import com.zhh.payment.mall.vo.OrderVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author zhh
 * @description 订单Controller
 * @date 2020-03-24 22:48
 */
@RestController
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @PostMapping("/orders")
    public ResponseVO<OrderVO> create(@Valid @RequestBody OrderCreateForm form,
                                      HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderServiceImpl.create(user.getId(), form.getShippingId());
    }

    @GetMapping("/orders")
    public ResponseVO<PageInfo> list(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderServiceImpl.list(user.getId(), pageNum, pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVO<OrderVO> detail(@PathVariable Long orderNo,
                                      HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderServiceImpl.detail(user.getId(), orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVO cancel(@PathVariable Long orderNo,
                             HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderServiceImpl.cancel(user.getId(), orderNo);
    }
}
