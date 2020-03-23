package com.zhh.payment.mall.controller;

import com.github.pagehelper.PageInfo;
import com.zhh.payment.mall.service.impl.ProductServiceImpl;
import com.zhh.payment.mall.vo.ProductDetailVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhh
 * @description 商品Controller
 * @date 2020-03-23 14:45
 */
@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    /**
     * 获取商品列表
     *
     * @param categoryId 类目ID
     * @param pageNum    当前页
     * @param pageSize   分页大小
     * @return
     */
    @GetMapping("/products")
    @ResponseBody
    public ResponseVO<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return productServiceImpl.list(categoryId, pageNum, pageSize);
    }

    /**
     * 获取商品详情
     *
     * @param productId 商品ID
     * @return
     */
    @GetMapping("/products/{productId}")
    public ResponseVO<ProductDetailVO> detail(Integer productId) {
        return productServiceImpl.detail(productId);
    }
}
