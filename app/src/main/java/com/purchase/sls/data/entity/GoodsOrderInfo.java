package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/7/7.
 */

public class GoodsOrderInfo implements Serializable{
    @SerializedName("power")
    private String power;
    @SerializedName("id")
    private String id;
    @SerializedName("goodsid")
    private String goodsid;
    @SerializedName("sku")
    private String sku;
    @SerializedName("skuinfo")
    private String skuinfo;
    @SerializedName("goodsnum")
    private String goodsnum;
    @SerializedName("allprice")
    private String allprice;
    @SerializedName("quan_price")
    private String quanPrice;
    @SerializedName("taobao_id")
    private String taobaoId;
    @SerializedName("quan_url")
    private String quan_url;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("goods_img")
    private String goodsImg;
    @SerializedName("price")
    private String price;

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuinfo() {
        return skuinfo;
    }

    public void setSkuinfo(String skuinfo) {
        this.skuinfo = skuinfo;
    }

    public String getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(String goodsnum) {
        this.goodsnum = goodsnum;
    }

    public String getAllprice() {
        return allprice;
    }

    public void setAllprice(String allprice) {
        this.allprice = allprice;
    }

    public String getQuanPrice() {
        return quanPrice;
    }

    public void setQuanPrice(String quanPrice) {
        this.quanPrice = quanPrice;
    }

    public String getTaobaoId() {
        return taobaoId;
    }

    public void setTaobaoId(String taobaoId) {
        this.taobaoId = taobaoId;
    }

    public String getQuan_url() {
        return quan_url;
    }

    public void setQuan_url(String quan_url) {
        this.quan_url = quan_url;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
