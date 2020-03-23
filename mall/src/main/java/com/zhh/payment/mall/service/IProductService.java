package com.zhh.payment.mall.service;

import com.github.pagehelper.PageInfo;
import com.zhh.payment.mall.vo.ProductDetailVO;
import com.zhh.payment.mall.vo.ResponseVO;

/**
 * @author zhh
 * @description 商品Service
 * @date 2020-03-23 12:41
 */
public interface IProductService {

    /**
     * 获取商品列表
     *
     * @param categoryId 类目ID
     * @param pageNum    当前页
     * @param pageSize   分页大小
     * @return
     */
    ResponseVO<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    /**
     * 获取商品详情
     *
     * @param productId 商品ID
     * @return
     */
    ResponseVO<ProductDetailVO> detail(Integer productId);
}
