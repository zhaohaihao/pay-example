package com.zhh.payment.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhh.payment.mall.dao.OrderItemMapper;
import com.zhh.payment.mall.dao.OrderMapper;
import com.zhh.payment.mall.dao.ProductMapper;
import com.zhh.payment.mall.dao.ShippingMapper;
import com.zhh.payment.mall.enums.OrderStatusEnum;
import com.zhh.payment.mall.enums.PaymentTypeEnum;
import com.zhh.payment.mall.enums.ProductStatusEnum;
import com.zhh.payment.mall.pojo.*;
import com.zhh.payment.mall.service.IOrderService;
import com.zhh.payment.mall.vo.OrderItemVO;
import com.zhh.payment.mall.vo.OrderVO;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.zhh.payment.mall.enums.ResponseEnum.*;

/**
 * @author zhh
 * @description 订单Service实现类
 * @date 2020-03-24 12:28
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<OrderVO> create(
            Integer uid, Integer shippingId
    ) {
        // 收货地址校验
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if (shipping == null) {
            return ResponseVO.error(SHIPPING_NOT_EXIST);
        }

        // 购物车校验(是否有商品、库存)
        List<Cart> cartList = cartServiceImpl.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVO.error(CART_SELECTED_IS_EMPTY);
        }

        // 获取cartList中的productIds
        Set<Integer> productIdSet = cartList.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);

        Map<Integer, Product> map = productList.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        Long orderNo = generateOrderNo();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Product product = map.get(cart.getProductId());
            // 是否有该商品
            if (product == null) {
                return ResponseVO.error(PRODUCT_NOT_EXIST, "商品不存在, product_id = " + cart.getProductId());
            }

            // 商品上下架状态
            if (!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())) {
                return ResponseVO.error(PRODUCT_OFF_SALE_OR_DELETE, product.getName() + "商品不是在售状态");
            }

            // 库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVO.error(PRODUCT_STOCK_ERROR, product.getName() + "商品库存不足");
            }

            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);

            // 减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row == 0) {
                return ResponseVO.error(ERROR);
            }
        }

        // 计算总价格(只计算被选中的商品)
        // 生成订单, 入库: mall_order, mall_order_item, 利用事务保证两张表数据能够同时写入或失败
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        int row = orderMapper.insertSelective(order);
        if (row <= 0) {
            return ResponseVO.error(ERROR);
        }

        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowForOrderItem == 0) {
            return ResponseVO.error(ERROR);
        }

        // 更新购物车(删除选中的商品)
        // Redis有事务(打包命令), 不能回滚
        for (Cart cart : cartList) {
            cartServiceImpl.delete(uid, cart.getProductId());
        }

        // 构造orderVO
        OrderVO orderVO = buildOrderVO(order, orderItemList, shipping);
        return ResponseVO.success(orderVO);
    }

    @Override
    public ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);

        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNoSet);
        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));

        Set<Integer> shippingIdSet = orderList.stream().map(Order::getShippingId).collect(Collectors.toSet());
        List<Shipping> shippings = shippingMapper.selectByIdSet(shippingIdSet);
        Map<Integer, Shipping> shippingMap = shippings.stream()
                .collect(Collectors.toMap(Shipping::getId, shipping -> shipping));

        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVO orderVO = buildOrderVO(order, orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            orderVOList.add(orderVO);
        }

        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVOList);

        return ResponseVO.success(pageInfo);
    }

    @Override
    public ResponseVO<OrderVO> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)) {
            return ResponseVO.error(ORDER_NOT_EXIST);
        }

        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNoSet);

        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVO orderVO = buildOrderVO(order, orderItemList, shipping);

        return ResponseVO.success(orderVO);
    }

    @Override
    public ResponseVO cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)) {
            return ResponseVO.error(ORDER_NOT_EXIST);
        }

        // 这里规定只有未付款订单可以取消
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            return ResponseVO.error(ORDER_STATUS_ERROR);
        }

        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            return ResponseVO.error(ERROR);
        }

        return ResponseVO.success();
    }

    @Override
    public void paid(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            // 告警
            throw new RuntimeException(ORDER_NOT_EXIST.getDesc() + "订单ID: " + orderNo);
        }

        // 这里规定只有未付款订单可以变成已付款
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            throw new RuntimeException(ORDER_STATUS_ERROR.getDesc() + "订单ID: " + orderNo);
        }

        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPaymentTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            throw new RuntimeException("将订单更新为已支付状态失败订单ID: " + orderNo);
        }
    }

    /**
     * 生成订单号
     *
     * 企业级: 分布式唯一id/主键
     *
     * @return
     */
    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    /**
     * 构建订单详情
     *
     * @param uid      用户ID
     * @param orderNo  订单号
     * @param quantity 数量
     * @param product  商品
     * @return
     */
    private OrderItem buildOrderItem(Integer uid, Long orderNo, Integer quantity, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setUserId(uid);
        orderItem.setOrderNo(orderNo);
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setQuantity(quantity);
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }

    /**
     * 构建订单
     *
     * @param uid           用户ID
     * @param orderNo       订单号
     * @param shippingId    收货地址ID
     * @param orderItemList 订单详情列表
     * @return
     */
    private Order buildOrder(Integer uid, Long orderNo, Integer shippingId, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        return order;
    }

    /**
     * 创建订单VO
     *
     * @param order         订单
     * @param orderItemList 订单详情列表
     * @param shipping      收货地址
     * @return
     */
    private OrderVO buildOrderVO(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);

        List<OrderItemVO> OrderItemVOList = orderItemList.stream().map(e -> {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(e, orderItemVO);
            return orderItemVO;
        }).collect(Collectors.toList());
        orderVO.setOrderItemVOList(OrderItemVOList);

        if (shipping != null) {
            orderVO.setShippingId(shipping.getId());
            orderVO.setShippingVO(shipping);
        }

        return orderVO;
    }
}
