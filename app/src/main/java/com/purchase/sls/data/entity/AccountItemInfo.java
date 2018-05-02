package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountItemInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("uid")
    private String uid;
    @SerializedName("title")
    private String title;
    @SerializedName("z_pics")
    private String zPics;
    @SerializedName("price")
    private String price;
    @SerializedName("created_at")
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getzPics() {
        return zPics;
    }

    public void setzPics(String zPics) {
        this.zPics = zPics;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
