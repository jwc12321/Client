package com.purchase.sls.data.entity;


import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/28.
 */

public class GeneratingOrderInfo {
    @SerializedName("sign")
    private String sign;
    @SerializedName("orderno")
    private String orderno;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}
