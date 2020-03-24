package com.zhh.payment.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhh.payment.mall.dao.ShippingMapper;
import com.zhh.payment.mall.form.ShippingForm;
import com.zhh.payment.mall.pojo.Shipping;
import com.zhh.payment.mall.service.IShippingService;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhh.payment.mall.enums.ResponseEnum.DELETE_SHIPPING_FAIL;
import static com.zhh.payment.mall.enums.ResponseEnum.ERROR;

/**
 * @author zhh
 * @description 收货地址Service实现类
 * @date 2020-03-24 11:29
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVO<Map<String, Integer>> add(
            Integer uid, ShippingForm form
    ) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);

        int row = shippingMapper.insertSelective(shipping);
        if (row == 0) {
            return ResponseVO.error(ERROR);
        }

        Map<String, Integer> data = new HashMap<>();
        data.put("shippingId", shipping.getId());

        return ResponseVO.success(data);
    }

    @Override
    public ResponseVO delete(Integer uid, Integer shippingId) {
        int row = shippingMapper.deleteByIdAndUid(uid, shippingId);
        if (row == 0) {
            return ResponseVO.error(DELETE_SHIPPING_FAIL);
        }
        return ResponseVO.success();
    }

    @Override
    public ResponseVO update(Integer uid, Integer shippingId, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);

        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (row == 0) {
            return ResponseVO.error(ERROR);
        }

        return ResponseVO.success();
    }

    @Override
    public ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippings = shippingMapper.selectByUid(uid);
        PageInfo pageInfo = new PageInfo<>(shippings);
        return ResponseVO.success(pageInfo);
    }
}
