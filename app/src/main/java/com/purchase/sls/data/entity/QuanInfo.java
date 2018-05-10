package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/7.
 */

public class QuanInfo {
    @SerializedName("id")
    private String id;
    //优惠金额
    @SerializedName("price")
    private String price;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("endtime")
    private String endtime;
    @SerializedName("condition")
    private String condition;
    @SerializedName("pic")
    private String pic;
    @SerializedName("status")
    private String status;
    @SerializedName("suitbusiness")
    private String suitbusiness;
    @SerializedName("title")
    private String title;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("starttime")
    private String starttime;
    //几天有效
    @SerializedName("validday")
    private String validday;
    //满多少可用
    @SerializedName("least_cost")
    private String leastCost;
    @SerializedName("deleted_at")
    private String deletedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuitbusiness() {
        return suitbusiness;
    }

    public void setSuitbusiness(String suitbusiness) {
        this.suitbusiness = suitbusiness;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getValidday() {
        return validday;
    }

    public void setValidday(String validday) {
        this.validday = validday;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLeastCost() {
        return leastCost;
    }

    public void setLeastCost(String leastCost) {
        this.leastCost = leastCost;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
