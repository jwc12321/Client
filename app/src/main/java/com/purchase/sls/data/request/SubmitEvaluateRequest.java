package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/5/5.
 */

public class SubmitEvaluateRequest {
    @SerializedName("storeid")
    private String storeid;
    @SerializedName("words")
    private String words;
    @SerializedName("starts")
    private String starts;
    @SerializedName("pics")
    private String[] pics;
    @SerializedName("type")
    private String type;
    @SerializedName("orderid")
    private String orderid;

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public String[] getPics() {
        return pics;
    }

    public void setPics(String[] pics) {
        this.pics = pics;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
