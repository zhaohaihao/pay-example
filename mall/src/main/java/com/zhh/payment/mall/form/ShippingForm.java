package com.zhh.payment.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhh
 * @description 收获地址表单
 * @date 2020-03-24 11:28
 */
@Data
public class ShippingForm {

    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverCity;

    @NotBlank
    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverZip;
}
