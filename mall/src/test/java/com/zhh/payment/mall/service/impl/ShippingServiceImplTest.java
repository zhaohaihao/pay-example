package com.zhh.payment.mall.service.impl;

import com.zhh.payment.mall.MallApplicationTest;
import com.zhh.payment.mall.enums.ResponseEnum;
import com.zhh.payment.mall.form.ShippingForm;
import com.zhh.payment.mall.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author zhh
 * @description
 * @date 2020-03-24 11:37
 */
public class ShippingServiceImplTest extends MallApplicationTest {

    @Autowired
    private ShippingServiceImpl shippingServiceImpl;

    private Integer uid = 1;

    @Test
    public void add() {
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverName("黑豪");
        shippingForm.setReceiverPhone("12345678");
        shippingForm.setReceiverMobile("150****9876");
        shippingForm.setReceiverProvince("浙江");
        shippingForm.setReceiverCity("杭州");
        shippingForm.setReceiverDistrict("江干区");
        shippingForm.setReceiverAddress("某某街道某某小区");
        shippingForm.setReceiverZip("000000");
        ResponseVO<Map<String, Integer>> responseVO = shippingServiceImpl.add(uid, shippingForm);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }

    @Test
    public void delete() {
        ResponseVO responseVO = shippingServiceImpl.delete(uid, 10);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }

    @Test
    public void update() {
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverName("Horace");
        ResponseVO responseVO = shippingServiceImpl.update(uid, 1, shippingForm);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }

    @Test
    public void list() {
        ResponseVO responseVO = shippingServiceImpl.list(uid, 1, 10);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }
}