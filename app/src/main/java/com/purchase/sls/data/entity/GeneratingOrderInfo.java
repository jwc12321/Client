package com.purchase.sls.data.entity;


import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/28.
 */

public class GeneratingOrderInfo {
    @SerializedName("orderno")
    private String orderno;
    
    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}
