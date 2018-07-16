package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/7/7.
 */

public class PurchaseGoodsRequest implements Serializable{
    //数量
    @SerializedName("goodsnum")
    private String goodsnum;
    //商品id
    @SerializedName("goods_id")
    private String goodsId;
    //skuid
    @SerializedName("sku")
    private String sku;
    //价格
    @SerializedName("price")
    private String price;
    //规格属性
    @SerializedName("skuinfo")
    private String skuinfo;
    //淘宝商品id
    @SerializedName("taobaoid")
    private String taobaoid;
    //优惠券价格
    @SerializedName("quan")
    private String quan;

    @SerializedName("quanUrl")
    private String quan_url;

    public PurchaseGoodsRequest(String goodsnum, String goodsId, String sku, String price, String skuinfo, String taobaoid, String quan, String quan_url) {
        this.goodsnum = goodsnum;
        this.goodsId = goodsId;
        this.sku = sku;
        this.price = price;
        this.skuinfo = skuinfo;
        this.taobaoid = taobaoid;
        this.quan = quan;
        this.quan_url = quan_url;
    }
}
