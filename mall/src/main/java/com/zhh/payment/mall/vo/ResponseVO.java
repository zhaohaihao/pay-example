package com.zhh.payment.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zhh.payment.mall.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @author zhh
 * @description 返回结果VO
 * @date 2020-03-22 18:16
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {

    private Integer status;

    private String msg;

    private T data;

    public ResponseVO(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * 响应成功
     *
     * @return
     */
    public static <T> ResponseVO<T> success() {
        return new ResponseVO(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getDesc());
    }

    /**
     * 响应成功
     *
     * @param msg 响应信息
     * @return
     */
    public static <T> ResponseVO<T> success(String msg) {
        return new ResponseVO(ResponseEnum.SUCCESS.getCode(), msg);
    }

    /**
     * 响应失败
     *
     * @param responseEnum 响应信息枚举
     * @return
     */
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum) {
        return new ResponseVO(responseEnum.getCode(), responseEnum.getDesc());
    }

    /**
     * 响应失败
     *
     * @param responseEnum 响应信息枚举
     * @param msg          响应信息
     * @return
     */
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum, String msg) {
        return new ResponseVO(responseEnum.getCode(), msg);
    }

    /**
     * 响应失败
     *
     * @param responseEnum  响应信息枚举
     * @param bindingResult
     * @return
     */
    public static <T> ResponseVO<T> error(ResponseEnum responseEnum, BindingResult bindingResult) {
        return new ResponseVO(responseEnum.getCode(),
                Objects.requireNonNull(bindingResult.getFieldError()).getField() + " " + bindingResult.getFieldError().getDefaultMessage());
    }
}
