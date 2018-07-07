package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2018/7/7.
 */

public class GoodsOrderList implements Serializable{
    @SerializedName("list")
    private List<GoodsOrderInfo> goodsOrderInfos;
    @SerializedName("quanhou")
    private String quanhou;
    @SerializedName("quan")
    private String quan;
    @SerializedName("ownquan")
    private String ownquan;
    @SerializedName("cartid")
    private String cartid;

    public List<GoodsOrderInfo> getGoodsOrderInfos() {
        return goodsOrderInfos;
    }

    public void setGoodsOrderInfos(List<GoodsOrderInfo> goodsOrderInfos) {
        this.goodsOrderInfos = goodsOrderInfos;
    }

    public String getQuanhou() {
        return quanhou;
    }

    public void setQuanhou(String quanhou) {
        this.quanhou = quanhou;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getOwnquan() {
        return ownquan;
    }

    public void setOwnquan(String ownquan) {
        this.ownquan = ownquan;
    }

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }
}
