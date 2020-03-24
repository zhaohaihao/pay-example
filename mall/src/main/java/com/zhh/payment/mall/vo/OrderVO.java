package com.zhh.payment.mall.vo;

import com.zhh.payment.mall.pojo.Shipping;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhh
 * @description 订单VO
 * @date 2020-03-24 12:23
 */
@Data
public class OrderVO {

    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private List<OrderItemVO> orderItemVOList;

    private Integer shippingId;

    private Shipping shippingVO;
}
