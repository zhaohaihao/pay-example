package com.zhh.payment.mall.config;

import com.zhh.payment.mall.consts.MallConst;
import com.zhh.payment.mall.exception.UserLoginException;
import com.zhh.payment.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zhh
 * @description 用户登录拦截器
 * @date 2020-03-22 23:13
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler
    ) throws Exception {
        log.info("进入拦截方法...");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            log.info("用户为空");
            throw new UserLoginException();
        }
        return true;
    }
}
