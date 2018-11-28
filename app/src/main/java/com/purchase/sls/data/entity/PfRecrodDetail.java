package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/11/27.
 */

public class PfRecrodDetail {
    //记录id
    @SerializedName("id")
    private String id;
    //用户id
    @SerializedName("uid")
    private String uid;
    //用户银行卡id
    @SerializedName("user_bank_id")
    private String userBankId;
    //提现金额
    @SerializedName("price")
    private String price;
    //备注
    @SerializedName("remark")
    private String remark;
    //审批状态 0：待审核 1：审核通过 2：已打款 -1审核未通过 -2打款失败
    @SerializedName("status")
    private String status;
    //后台审核id
    @SerializedName("adminid")
    private String adminid;
    //创建时间
    @SerializedName("created_at")
    private String createdAt;
    //最后更新时间
    @SerializedName("update_at")
    private String updateAt;
    //提现银行
    @SerializedName("bank_name")
    private String bankName;
    //提现银行卡号
    @SerializedName("bank_number")
    private String bankNumber;
    //用户账号
    @SerializedName("username")
    private String username;
    //提现单号
    @SerializedName("withdraw_no")
    private String withdrawNo;

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

    public String getUserBankId() {
        return userBankId;
    }

    public void setUserBankId(String userBankId) {
        this.userBankId = userBankId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWithdrawNo() {
        return withdrawNo;
    }

    public void setWithdrawNo(String withdrawNo) {
        this.withdrawNo = withdrawNo;
    }
}
