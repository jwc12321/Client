package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/23.
 */

public class ScreeningListResponse {
    @SerializedName("stores")
    private LikeStoreResponse likeStoreResponse;

    public LikeStoreResponse getLikeStoreResponse() {
        return likeStoreResponse;
    }

    public void setLikeStoreResponse(LikeStoreResponse likeStoreResponse) {
        this.likeStoreResponse = likeStoreResponse;
    }
}
