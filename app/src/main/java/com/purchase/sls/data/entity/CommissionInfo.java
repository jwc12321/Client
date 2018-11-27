package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/11/27.
 */

public class CommissionInfo {
    @SerializedName("balanceMoney")
    private String balanceMoney;

    public String getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(String balanceMoney) {
        this.balanceMoney = balanceMoney;
    }
}
