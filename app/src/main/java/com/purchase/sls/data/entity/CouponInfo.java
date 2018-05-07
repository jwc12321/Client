package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.lang.ref.PhantomReference;

/**
 * Created by JWC on 2018/4/28.
 * 优惠券
 */

public class CouponInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("uid")
    private String uid;
    @SerializedName("qid")
    private String qid;
    @SerializedName("expire_at")
    private String expire_at;
    //0:可用
    @SerializedName("status")
    private String status;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("quan")
    private QuanInfo quanInfo;

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

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(String expire_at) {
        this.expire_at = expire_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public QuanInfo getQuanInfo() {
        return quanInfo;
    }

    public void setQuanInfo(QuanInfo quanInfo) {
        this.quanInfo = quanInfo;
    }
}
