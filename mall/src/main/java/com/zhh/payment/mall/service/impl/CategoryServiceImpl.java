package com.zhh.payment.mall.service.impl;

import com.zhh.payment.mall.dao.CategoryMapper;
import com.zhh.payment.mall.pojo.Category;
import com.zhh.payment.mall.service.ICategoryService;
import com.zhh.payment.mall.vo.CategoryVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zhh.payment.mall.consts.MallConst.ROOT_PARENT_ID;

/**
 * @author zhh
 * @description 类目Service实现类
 * @date 2020-03-22 23:48
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 关于耗时问题
    // http(请求微信api) > 磁盘 > 内存
    //
    // mysql(内网+磁盘)


    @Override
    public ResponseVO<List<CategoryVO>> findAll() {
        List<Category> categoryList = categoryMapper.selectAll();

        // 查出parentId = 0的数据
        List<CategoryVO> categoryVOList = categoryList.stream()
                .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
                .map(this::category2CategoryVO)
                .sorted(Comparator.comparing(CategoryVO::getSortOrder).reversed())
                .collect(Collectors.toList());

        // 查询子目录
        findSubCategory(categoryVOList, categoryList);

        return ResponseVO.success(categoryVOList);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categoryList = categoryMapper.selectAll();
        findSubCategoryId(id, resultSet, categoryList);
    }

    /**
     * 查找子目录id集
     *
     * @param id           查询的类目ID
     * @param resultSet    查询子目录ID集
     * @param categoryList 类目列表
     */
    private void findSubCategoryId(Integer id, Set<Integer> resultSet, List<Category> categoryList) {
        for (Category category : categoryList) {
            if (category.getParentId().equals(id)) {
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(), resultSet, categoryList);
            }
        }
    }

    /**
     * 查找子目录
     *
     * @param categoryVOList
     * @param categoryList
     */
    private void findSubCategory(List<CategoryVO> categoryVOList, List<Category> categoryList) {
        for (CategoryVO categoryVO : categoryVOList) {

            List<CategoryVO> subCategories = new ArrayList<>();
            for (Category category : categoryList) {
                if (categoryVO.getId().equals(category.getParentId())) {
                    subCategories.add(category2CategoryVO(category));
                }

                categoryVO.setSubCategories(subCategories);

                // 按照sortOrder降序排序
                subCategories.sort(Comparator.comparing(CategoryVO::getSortOrder).reversed());

                // 递归查询子目录
                findSubCategory(subCategories, categoryList);
            }
        }
    }

    private CategoryVO category2CategoryVO(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        return categoryVO;
    }
}
