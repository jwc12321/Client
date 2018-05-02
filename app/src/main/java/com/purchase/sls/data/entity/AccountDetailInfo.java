package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/2.
 */

public class AccountDetailInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("allprice")
    private String allprice;
    @SerializedName("price")
    private String price;
    @SerializedName("power")
    private String power;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("username")
    private String username;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("title")
    private String title;
    @SerializedName("z_pics")
    private String zPics;
    @SerializedName("storeid")
    private String storeid;
    @SerializedName("orderno")
    private String orderno;
    @SerializedName("quannum")
    private String quannum;
    @SerializedName("paytype")
    private String paytype;
    @SerializedName("uid")
    private String uid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAllprice() {
        return allprice;
    }

    public void setAllprice(String allprice) {
        this.allprice = allprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getQuannum() {
        return quannum;
    }

    public void setQuannum(String quannum) {
        this.quannum = quannum;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
