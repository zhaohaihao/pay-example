package com.zhh.payment.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhh.payment.mall.MallApplicationTest;
import com.zhh.payment.mall.enums.ResponseEnum;
import com.zhh.payment.mall.vo.OrderVO;
import com.zhh.payment.mall.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhh
 * @description
 * @date 2020-03-24 19:18
 */
@Slf4j
public class OrderServiceImplTest extends MallApplicationTest {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    private Integer uid = 1;

    private Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void create() {
        ResponseVO<OrderVO> responseVO = orderServiceImpl.create(uid, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }

    @Test
    public void list() {
        ResponseVO<PageInfo> responseVO = orderServiceImpl.list(uid, 1, 10);
        log.info("result={}", GSON.toJson(responseVO));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }

    @Test
    public void detail() {
        ResponseVO<OrderVO> responseVO = orderServiceImpl.detail(uid, 1585056614074L);
        log.info("result={}", GSON.toJson(responseVO));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }

    @Test
    public void cancel() {
        ResponseVO<OrderVO> responseVO = orderServiceImpl.cancel(uid, 1585056614074L);
        log.info("result={}", GSON.toJson(responseVO));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVO.getStatus());
    }

    @Test
    public void paid() {
    }
}