package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/7/6.
 */

public class GoodsUnitPrice implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("sku_id")
    private String skuId;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("price")
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
