package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/12/12.
 */

public class GoodsShareLinkRequest {
    @SerializedName("userid")
    private String userid;
    @SerializedName("goodsid")
    private String goodsid;

    public GoodsShareLinkRequest(String userid, String goodsid) {
        this.userid = userid;
        this.goodsid = goodsid;
    }
}
