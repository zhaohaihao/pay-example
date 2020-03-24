package com.zhh.payment.mall.service;

import com.github.pagehelper.PageInfo;
import com.zhh.payment.mall.form.ShippingForm;
import com.zhh.payment.mall.vo.ResponseVO;

import java.util.Map;

/**
 * @author zhh
 * @description 收货地址Service接口
 * @date 2020-03-24 11:29
 */
public interface IShippingService {

    ResponseVO<Map<String, Integer>> add(Integer uid, ShippingForm form);

    ResponseVO delete(Integer uid, Integer shippingId);

    ResponseVO update(Integer uid, Integer shippingId, ShippingForm form);

    ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}
