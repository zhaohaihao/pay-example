package com.zhh.payment.mall.exception;

import com.zhh.payment.mall.enums.ResponseEnum;
import com.zhh.payment.mall.vo.ResponseVO;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhh
 * @description 全局异常处理
 * @date 2020-03-22 22:20
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 运行时异常处理
     * @param e 运行时异常
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseVO handleRuntimeException(RuntimeException e) {
        return ResponseVO.error(ResponseEnum.ERROR, e.getMessage());
    }

    /**
     * 用户登录异常处理
     *
     * @param e 用户登录异常
     * @return
     */
    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVO handleUserLoginException(UserLoginException e) {
        return ResponseVO.error(ResponseEnum.NEED_LOGIN);
    }

    /**
     * 表单验证异常处理
     *
     * @param e 表单验证异常
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseVO.error(ResponseEnum.ERROR, e.getBindingResult());
    }
}
