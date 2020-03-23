package com.zhh.payment.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.zhh.payment.mall.MallApplicationTest;
import com.zhh.payment.mall.enums.ResponseEnum;
import com.zhh.payment.mall.vo.ProductDetailVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhh
 * @description
 * @date 2020-03-23 13:50
 */
public class ProductServiceImplTest extends MallApplicationTest {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Test
    public void list() {
        ResponseVO<PageInfo> responseVO = productServiceImpl.list(100002, 1, 10);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }

    @Test
    public void detail() {
        ResponseVO<ProductDetailVO> responseVO = productServiceImpl.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }
}