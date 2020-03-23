package com.zhh.payment.mall.service.impl;

import com.zhh.payment.mall.MallApplicationTest;
import com.zhh.payment.mall.enums.ResponseEnum;
import com.zhh.payment.mall.vo.CategoryVO;
import com.zhh.payment.mall.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhh
 * @description
 * @date 2020-03-23 00:25
 */
@Slf4j
public class CategoryServiceImplTest extends MallApplicationTest {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    public void findAll() {
        ResponseVO<List<CategoryVO>> responseVO = categoryServiceImpl.findAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }

    @Test
    public void findSubCategoryId() {
        Set<Integer> integerSet = new HashSet<>();
        categoryServiceImpl.findSubCategoryId(100001, integerSet);
        log.info("set={}", integerSet);
    }
}