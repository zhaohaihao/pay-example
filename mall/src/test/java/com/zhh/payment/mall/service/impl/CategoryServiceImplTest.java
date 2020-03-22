package com.zhh.payment.mall.service.impl;

import com.zhh.payment.mall.enums.ResponseEnum;
import com.zhh.payment.mall.vo.CategoryVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhh
 * @description
 * @date 2020-03-23 00:25
 */
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    public void findAll() {
        ResponseVO<List<CategoryVO>> responseVO = categoryServiceImpl.findAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }
}