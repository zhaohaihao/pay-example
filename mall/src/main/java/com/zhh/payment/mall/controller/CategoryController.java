package com.zhh.payment.mall.controller;

import com.zhh.payment.mall.service.impl.CategoryServiceImpl;
import com.zhh.payment.mall.vo.CategoryVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhh
 * @description 类目Controller
 * @date 2020-03-22 23:55
 */
@RestController
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @GetMapping("/categories")
    public ResponseVO<List<CategoryVO>> findAll() {
        return categoryServiceImpl.findAll();
    }
}
