package com.zhh.payment.mall.service.impl;

import com.zhh.payment.mall.dao.UserMapper;
import com.zhh.payment.mall.enums.RoleEnum;
import com.zhh.payment.mall.pojo.User;
import com.zhh.payment.mall.service.IUserService;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.zhh.payment.mall.enums.ResponseEnum.*;

/**
 * @author zhh
 * @description 用户Service实现类
 * @date 2020-03-22 17:02
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVO<User> register(User user) {
        // username 不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            return ResponseVO.error(USERNAME_EXIST);
        }

        // email 不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVO.error(EMAIL_EXIST);
        }

        // MD5摘要算法(Spring自带)
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        // 默认角色是普通用户
        user.setRole(RoleEnum.CUSTOMER.getCode());

        // 写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            return ResponseVO.error(ERROR);
        }

        return ResponseVO.success();
    }

    @Override
    public ResponseVO<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            // 用户不存在
            return ResponseVO.error(USERNAME_OR_PASSWORD_ERROR);
        }

        if (user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)))) {
            // 密码错误
            return ResponseVO.error(USERNAME_OR_PASSWORD_ERROR);
        }

        user.setPassword("");
        return ResponseVO.success(user);
    }
}
