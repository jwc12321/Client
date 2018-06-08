package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/7.
 */

public class ActivityOrderInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("goods_logo")
    private String goodsLogo;
    @SerializedName("price")
    private String price;
    @SerializedName("act_type")
    private String actType;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("goods_price")
    private String goodsPrice;
    @SerializedName("status")
    private String status;
    @SerializedName("orderNum")
    private String orderNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGoodsLogo() {
        return goodsLogo;
    }

    public void setGoodsLogo(String goodsLogo) {
        this.goodsLogo = goodsLogo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
