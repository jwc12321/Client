package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by JWC on 2018/7/5.
 */

public class GoodsDetailInfo implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("quan_price")
    private String quanPrice;
    @SerializedName("price")
    private String price;
    @SerializedName("goods_img")
    private String goodsImg;
    @SerializedName("endtime")
    private String endtime;
    //已售
    @SerializedName("salenum")
    private String salenum;
    @SerializedName("sku")
    private GoodsSku goodsSku;

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

    public String getQuanPrice() {
        return quanPrice;
    }

    public void setQuanPrice(String quanPrice) {
        this.quanPrice = quanPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getSalenum() {
        return salenum;
    }

    public void setSalenum(String salenum) {
        this.salenum = salenum;
    }

    public GoodsSku getGoodsSku() {
        return goodsSku;
    }

    public void setGoodsSku(GoodsSku goodsSku) {
        this.goodsSku = goodsSku;
    }
}
