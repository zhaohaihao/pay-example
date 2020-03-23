package com.zhh.payment.mall.service;

import com.zhh.payment.mall.vo.CategoryVO;
import com.zhh.payment.mall.vo.ResponseVO;

import java.util.List;
import java.util.Set;

/**
 * @author zhh
 * @description 类目Service接口
 * @date 2020-03-22 23:47
 */
public interface ICategoryService {

    /**
     * 查询所有的类目
     * @return
     */
    ResponseVO<List<CategoryVO>> findAll();

    /**
     * 查找子目录id集
     *
     * @param id           查询的类目ID
     * @param resultSet    查询子目录ID集
     */
    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
