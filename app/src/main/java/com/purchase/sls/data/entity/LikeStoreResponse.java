package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/20.
 */

public class LikeStoreResponse {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<CollectionStoreInfo> collectionStoreInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<CollectionStoreInfo> getCollectionStoreInfos() {
        return collectionStoreInfos;
    }

    public void setCollectionStoreInfos(List<CollectionStoreInfo> collectionStoreInfos) {
        this.collectionStoreInfos = collectionStoreInfos;
    }
}
