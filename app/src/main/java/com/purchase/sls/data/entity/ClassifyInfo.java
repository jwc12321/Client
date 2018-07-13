package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/7/13.
 */

public class ClassifyInfo {
    @SerializedName("cate")
    private List<CateInfo> cateInfos;
    @SerializedName("sort")
    private List<SortInfo> sortInfos;
    @SerializedName("screen")
    private List<ScreenInfo> screenInfos;

    public List<CateInfo> getCateInfos() {
        return cateInfos;
    }

    public void setCateInfos(List<CateInfo> cateInfos) {
        this.cateInfos = cateInfos;
    }

    public List<SortInfo> getSortInfos() {
        return sortInfos;
    }

    public void setSortInfos(List<SortInfo> sortInfos) {
        this.sortInfos = sortInfos;
    }

    public List<ScreenInfo> getScreenInfos() {
        return screenInfos;
    }

    public void setScreenInfos(List<ScreenInfo> screenInfos) {
        this.screenInfos = screenInfos;
    }
}
