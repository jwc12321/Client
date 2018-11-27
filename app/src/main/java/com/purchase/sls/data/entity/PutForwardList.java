package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/11/27.
 */

public class PutForwardList {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<PfRecrodInfo> pfRecrodInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<PfRecrodInfo> getPfRecrodInfos() {
        return pfRecrodInfos;
    }

    public void setPfRecrodInfos(List<PfRecrodInfo> pfRecrodInfos) {
        this.pfRecrodInfos = pfRecrodInfos;
    }
}
