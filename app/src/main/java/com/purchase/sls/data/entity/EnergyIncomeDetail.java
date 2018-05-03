package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/3.
 */

public class EnergyIncomeDetail {
    @SerializedName("id")
    private String id;
    @SerializedName("uid")
    private String uid;
    @SerializedName("amount")
    private String amount;
    @SerializedName("camount")
    private String camount;
    @SerializedName("type")
    private String type;
    @SerializedName("pool")
    private String pool;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("orderno")
    private String orderno;
    @SerializedName("updated_at")
    private String updatedAt;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCamount() {
        return camount;
    }

    public void setCamount(String camount) {
        this.camount = camount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
