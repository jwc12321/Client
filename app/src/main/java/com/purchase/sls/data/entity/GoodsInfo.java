package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/9.
 */

public class GoodsInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("goods_img")
    private String goodsImg;
    @SerializedName("sku")
    private String sku;
    @SerializedName("goodsnum")
    private String goodsnum;
    @SerializedName("skuinfo")
    private String skuinfo;
    @SerializedName("wuliu")
    private String wuliu;

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

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(String goodsnum) {
        this.goodsnum = goodsnum;
    }

    public String getSkuinfo() {
        return skuinfo;
    }

    public void setSkuinfo(String skuinfo) {
        this.skuinfo = skuinfo;
    }

    public String getWuliu() {
        return wuliu;
    }

    public void setWuliu(String wuliu) {
        this.wuliu = wuliu;
    }
}
