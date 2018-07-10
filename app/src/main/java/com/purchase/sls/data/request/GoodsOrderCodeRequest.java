package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/10.
 */

public class GoodsOrderCodeRequest {
    @SerializedName("ordercode")
    private String ordercode;

    public GoodsOrderCodeRequest(String ordercode) {
        this.ordercode = ordercode;
    }
}
