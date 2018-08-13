package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/23.
 */

public class ScreeningListResponse {
    @SerializedName("stores")
    private LikeStoreResponse likeStoreResponse;
    @SerializedName("topcate")
    private List<TopCateInfo> topCateInfos;

    public LikeStoreResponse getLikeStoreResponse() {
        return likeStoreResponse;
    }

    public void setLikeStoreResponse(LikeStoreResponse likeStoreResponse) {
        this.likeStoreResponse = likeStoreResponse;
    }

    public List<TopCateInfo> getTopCateInfos() {
        return topCateInfos;
    }

    public void setTopCateInfos(List<TopCateInfo> topCateInfos) {
        this.topCateInfos = topCateInfos;
    }
}
