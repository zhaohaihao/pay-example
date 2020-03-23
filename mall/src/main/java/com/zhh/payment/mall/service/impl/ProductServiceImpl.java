package com.zhh.payment.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhh.payment.mall.dao.ProductMapper;
import com.zhh.payment.mall.enums.ProductStatusEnum;
import com.zhh.payment.mall.pojo.Product;
import com.zhh.payment.mall.service.IProductService;
import com.zhh.payment.mall.vo.ProductDetailVO;
import com.zhh.payment.mall.vo.ProductVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zhh.payment.mall.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

/**
 * @author zhh
 * @description 商品Service实现类
 * @date 2020-03-23 12:43
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Override
    public ResponseVO<PageInfo> list(
            Integer categoryId, Integer pageNum, Integer pageSize
    ) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            categoryServiceImpl.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVO> productVOList = productList.stream()
                                            .map(e -> {
                                                ProductVO productVO = new ProductVO();
                                                BeanUtils.copyProperties(e, productVO);
                                                return productVO;
                                            })
                                            .collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productVOList);

        return ResponseVO.success(pageInfo);
    }

    @Override
    public ResponseVO<ProductDetailVO> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        if (product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode())
            || product.getStatus().equals(ProductStatusEnum.DELETE.getCode())) {
            return ResponseVO.error(PRODUCT_OFF_SALE_OR_DELETE);
        }

        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(product, productDetailVO);

        // 对于敏感数据的处理
        productDetailVO.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVO.success(productDetailVO);
    }
}
