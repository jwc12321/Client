package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/7/6.
 */

public class ShoppingCartInfo implements Serializable{
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
    @SerializedName("oneprice")
    private String oneprice;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("goods_img")
    private String goodsImg;
    @SerializedName("price")
    private String price;

    //选中
    private boolean isChoosed;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getOneprice() {
        return oneprice;
    }

    public void setOneprice(String oneprice) {
        this.oneprice = oneprice;
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

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}

