package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

public class HotSearchInfo {
    @SerializedName("keywords")
    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
