package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/12/11.
 */

public class MallCategoryInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("catename")
    private String catename;
    @SerializedName("picurl")
    private String picurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
}
