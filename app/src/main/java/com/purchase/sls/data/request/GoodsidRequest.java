package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/5.
 */

public class GoodsidRequest {
    @SerializedName("goodsid")
    private String goodsid;

    public GoodsidRequest(String goodsid) {
        this.goodsid = goodsid;
    }
}
