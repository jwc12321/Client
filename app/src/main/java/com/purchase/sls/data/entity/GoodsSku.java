package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2018/7/6.
 */

public class GoodsSku implements Serializable {
    @SerializedName("newsku")
    private List<GoodsSpec> goodsSpecs;
    @SerializedName("propskumapping")
    private List<GoodsUnitPrice> goodsUnitPrices;

    public List<GoodsSpec> getGoodsSpecs() {
        return goodsSpecs;
    }

    public void setGoodsSpecs(List<GoodsSpec> goodsSpecs) {
        this.goodsSpecs = goodsSpecs;
    }

    public List<GoodsUnitPrice> getGoodsUnitPrices() {
        return goodsUnitPrices;
    }

    public void setGoodsUnitPrices(List<GoodsUnitPrice> goodsUnitPrices) {
        this.goodsUnitPrices = goodsUnitPrices;
    }
}
