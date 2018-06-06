package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2018/6/4.
 */

public class ActivityInfo implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("p_id")
    private String pId;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;
    @SerializedName("count")
    private String count;
    @SerializedName("price")
    private String price;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("act_logo")
    private String actLogo;
    @SerializedName("p_name")
    private String pName;
    @SerializedName("p_price")
    private String pPrice;
    @SerializedName("p_count")
    private String pCount;
    @SerializedName("p_pics")
    private String pPics;
    @SerializedName("p_logo")
    private String pLogo;
    @SerializedName("p_detail")
    private String pDetail;
    @SerializedName("p_status")
    private String pStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getActLogo() {
        return actLogo;
    }

    public void setActLogo(String actLogo) {
        this.actLogo = actLogo;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpCount() {
        return pCount;
    }

    public void setpCount(String pCount) {
        this.pCount = pCount;
    }

    public String getpPics() {
        return pPics;
    }

    public void setpPics(String pPics) {
        this.pPics = pPics;
    }

    public String getpLogo() {
        return pLogo;
    }

    public void setpLogo(String pLogo) {
        this.pLogo = pLogo;
    }

    public String getpDetail() {
        return pDetail;
    }

    public void setpDetail(String pDetail) {
        this.pDetail = pDetail;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }
}
