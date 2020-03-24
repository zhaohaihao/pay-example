package com.zhh.payment.mall.dao;

import com.zhh.payment.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByIdAndUid(@Param("uid") Integer uid, @Param("shippingId") Integer shippingId);

    List<Shipping> selectByUid(@Param("uid") Integer uid);

    Shipping selectByUidAndShippingId(@Param("uid") Integer uid, @Param("shippingId") Integer shippingId);

    List<Shipping> selectByIdSet(@Param("idSet") Set<Integer> idSet);
}