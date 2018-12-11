package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/12/11.
 */

public class MallBannerInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("banner")
    private String banner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
