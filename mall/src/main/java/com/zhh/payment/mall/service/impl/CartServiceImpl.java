package com.zhh.payment.mall.service.impl;

import com.google.gson.Gson;
import com.zhh.payment.mall.dao.ProductMapper;
import com.zhh.payment.mall.enums.ProductStatusEnum;
import com.zhh.payment.mall.form.CartAddForm;
import com.zhh.payment.mall.form.CartUpdateForm;
import com.zhh.payment.mall.pojo.Cart;
import com.zhh.payment.mall.pojo.Product;
import com.zhh.payment.mall.service.ICartService;
import com.zhh.payment.mall.vo.CartProductVO;
import com.zhh.payment.mall.vo.CartVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zhh.payment.mall.enums.ResponseEnum.*;

/**
 * @author zhh
 * @description 购物车Service实现类
 * @date 2020-03-23 15:40
 */
@Service
public class CartServiceImpl implements ICartService {

    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    private static final Gson GSON = new Gson();

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseVO<CartVO> add(Integer uid, CartAddForm cartAddForm) {
        Integer quantity = 1;
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());

        // 判断商品是否存在
        if (product == null) {
            return ResponseVO.error(PRODUCT_NOT_EXIST);
        }

        // 商品是否正常在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVO.error(PRODUCT_OFF_SALE_OR_DELETE);
        }

        // 商品库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVO.error(CART_PRODUCT_NOT_EXIST);
        }

        // 写入到redis
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        Cart cart;
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        if (StringUtils.isEmpty(value)) {
            // 没有该商品, 新增
            cart = new Cart(product.getId(), quantity, cartAddForm.getSelected());
        } else {
            // 已经有了, 数量累加
            cart = GSON.fromJson(value, Cart.class);
            cart.setProductId(cart.getQuantity() + quantity);
        }

        opsForHash.put(redisKey,
                String.valueOf(product.getId()),
                GSON.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        CartVO cartVO = new CartVO();
        List<CartProductVO> cartProductVOList = new ArrayList<>();

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = GSON.fromJson(entry.getValue(), Cart.class);

            // TODO 需要优化, 使用mysql里的in
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVO cartProductVO = new CartProductVO(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                );
                cartProductVOList.add(cartProductVO);

                if (!cart.getProductSelected()) {
                    selectAll = false;
                }

                // 计算总价(只计算选中的)
                if (cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVO.getProductTotalPrice());
                }
            }

            cartTotalQuantity += cart.getQuantity();
        }

        // 有一个没有选中，就不叫全选
        cartVO.setSelectedAll(selectAll);
        cartVO.setCartTotalQuantity(cartTotalQuantity);
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVOList(cartProductVOList);

        return ResponseVO.success(cartVO);
    }

    @Override
    public ResponseVO<CartVO> update(
            Integer uid, Integer productId, CartUpdateForm cartUpdateForm
    ) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            // 没有该商品, 报错
            return ResponseVO.error(CART_PRODUCT_NOT_EXIST);
        }

        // 已经有了, 修改内容
        Cart cart = GSON.fromJson(value, Cart.class);

        if (cartUpdateForm.getQuantity() != null
            && cartUpdateForm.getQuantity() >= 0) {
            cart.setQuantity(cartUpdateForm.getQuantity());
        }

        if (cartUpdateForm.getSelected() != null) {
            cart.setProductSelected(cartUpdateForm.getSelected());
        }

        opsForHash.put(redisKey,
                String.valueOf(productId),
                GSON.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            // 没有该商品, 报错
            return ResponseVO.error(CART_PRODUCT_NOT_EXIST);
        }

        opsForHash.delete(redisKey, String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    GSON.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey,
                    String.valueOf(cart.getProductId()),
                    GSON.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVO<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVO.success(sum);
    }

    /**
     * 列出购物车列表
     *
     * @param uid 用户ID
     * @return
     */
    private List<Cart> listForCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(GSON.fromJson(entry.getValue(), Cart.class));
        }

        return cartList;
    }
}
