package com.zhh.payment.mall.service;

import com.github.pagehelper.PageInfo;
import com.zhh.payment.mall.vo.OrderVO;
import com.zhh.payment.mall.vo.ResponseVO;

/**
 * @author zhh
 * @description 订单Service接口
 * @date 2020-03-24 12:27
 */
public interface IOrderService {

    ResponseVO<OrderVO> create(Integer uid, Integer shippingId);

    ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    ResponseVO<OrderVO> detail(Integer uid, Long orderNo);

    ResponseVO cancel(Integer uid, Long orderNo);

    void paid(Long orderNo);
}
