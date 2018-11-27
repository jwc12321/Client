package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/11/27.
 */

public class BankCardInfo implements Serializable {
    //银行卡记录id
    @SerializedName("id")
    private String id;
    //用户id
    @SerializedName("uid")
    private String uid;
    //银行卡账号
    @SerializedName("bank_number")
    private String bankNumber;
    //银行名称
    @SerializedName("bank_name")
    private String bankName;
    //银行支行
    @SerializedName("bank_subbranch_name")
    private String bankSubbranchName;
    //持卡人手机号
    @SerializedName("mobile")
    private String mobile;
    //持卡人姓名
    @SerializedName("owner_name")
    private String ownerName;
    //0表示正常状态 可忽略
    @SerializedName("status")
    private String status;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("update_at")
    private String updateAt;

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

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankSubbranchName() {
        return bankSubbranchName;
    }

    public void setBankSubbranchName(String bankSubbranchName) {
        this.bankSubbranchName = bankSubbranchName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
