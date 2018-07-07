package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/7.
 */

public class AddToCartRequest {
    //商品id
    @SerializedName("id")
    private String id;
    //淘宝商品id
    @SerializedName("taobaoid")
    private String taobaoid;
    //skuid
    @SerializedName("sku")
    private String sku;
    //数量
    @SerializedName("num")
    private String num;
    //规格属性
    @SerializedName("skuinfo")
    private String skuinfo;
    //优惠券价格
    @SerializedName("quan")
    private String quan;
    //价格
    @SerializedName("tjprice")
    private String tjprice;
    @SerializedName("quan_url")
    private String quan_url;

    public AddToCartRequest(String id, String taobaoid, String sku, String num, String skuinfo, String quan, String tjprice, String quan_url) {
        this.id = id;
        this.taobaoid = taobaoid;
        this.sku = sku;
        this.num = num;
        this.skuinfo = skuinfo;
        this.quan = quan;
        this.tjprice = tjprice;
        this.quan_url = quan_url;
    }
}
