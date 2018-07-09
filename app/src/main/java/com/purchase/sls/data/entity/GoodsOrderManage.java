package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/7/9.
 */

public class GoodsOrderManage {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<GoodsOrderItemInfo> goodsOrderItemInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<GoodsOrderItemInfo> getGoodsOrderItemInfos() {
        return goodsOrderItemInfos;
    }

    public void setGoodsOrderItemInfos(List<GoodsOrderItemInfo> goodsOrderItemInfos) {
        this.goodsOrderItemInfos = goodsOrderItemInfos;
    }
}
