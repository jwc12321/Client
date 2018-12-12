package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/4.
 */

public class GoodsItemInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("price")
    private String price;
    @SerializedName("quan_price")
    private String quanPrice;
    @SerializedName("goods_img")
    private String goodsImg;
    //佣金
    @SerializedName("goods_most_commission")
    private String goodsMostCommission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuanPrice() {
        return quanPrice;
    }

    public void setQuanPrice(String quanPrice) {
        this.quanPrice = quanPrice;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsMostCommission() {
        return goodsMostCommission;
    }

    public void setGoodsMostCommission(String goodsMostCommission) {
        this.goodsMostCommission = goodsMostCommission;
    }
}
