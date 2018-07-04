package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/7/4.
 */

public class GoodsItemList {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<GoodsItemInfo> goodsItemInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<GoodsItemInfo> getGoodsItemInfos() {
        return goodsItemInfos;
    }

    public void setGoodsItemInfos(List<GoodsItemInfo> goodsItemInfos) {
        this.goodsItemInfos = goodsItemInfos;
    }
}
