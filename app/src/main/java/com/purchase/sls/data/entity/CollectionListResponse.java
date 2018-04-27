package com.purchase.sls.data.entity;

import android.view.accessibility.AccessibilityNodeInfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/27.
 */

public class CollectionListResponse {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<CollectionListInfo> collectionInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<CollectionListInfo> getCollectionInfos() {
        return collectionInfos;
    }

    public void setCollectionInfos(List<CollectionListInfo> collectionInfos) {
        this.collectionInfos = collectionInfos;
    }
}
