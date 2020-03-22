package com.zhh.payment.mall.dao;

import com.zhh.payment.mall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名统计用户数量
     *
     * @param username 用户名
     * @return
     */
    int countByUsername(String username);

    /**
     * 根据邮箱统计用户数量
     *
     * @param email 用户邮箱
     * @return
     */
    int countByEmail(String email);

    /**
     * 根据用户名查找用户信息
     *
     * @param username 用户名
     * @return
     */
    User selectByUsername(String username);
}