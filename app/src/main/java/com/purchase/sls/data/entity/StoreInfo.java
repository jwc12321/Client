package com.purchase.sls.data.entity;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/24.
 */

public class StoreInfo {
    @SerializedName("id")
    private String id;
    @Nullable
//    @SerializedName("pics")
//    private List<String> pics;
//    @SerializedName("hj_pics")
//    private List<String> hjPics ;
    @SerializedName("z_pics")
    private String zPics;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("address")
    private String address;
    @SerializedName("address_xy")
    private String addressXy;
    @SerializedName("buzz")
    private String buzz;
    @SerializedName("labels")
    private String labels;
    @SerializedName("pic_words")
    private String picWords;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("qrcode")
    private String qrcode;
    @SerializedName("cid")
    private String cid;
    @SerializedName("needknow")
    private String needknow;
    @SerializedName("rebate")
    private String rebate;
    @SerializedName("bid")
    private String bid;
    @SerializedName("status")
    private String status;
    @SerializedName("regionid")
    private String regionid;
    @SerializedName("cityname")
    private String cityname;
    @SerializedName("poiname")
    private String poiname;
//    @SerializedName("tj_pics")
//    private List<String> tjPics;
    @SerializedName("rank")
    private String rank;
    @SerializedName("average")
    private String average;
    @SerializedName("diner")
    private String diner;
    @SerializedName("address_x")
    private String addressX;
    @SerializedName("address_y")
    private String addressY;
    //是否已经收藏  1：收藏
    @SerializedName("favo")
    private String favo;
    @SerializedName("bcate")
    private BcateInfo bcateInfo;

    public String getFavo() {
        return favo;
    }

    public void setFavo(String favo) {
        this.favo = favo;
    }

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public List<String> getPics() {
//        return pics;
//    }
//
//    public void setPics(List<String> pics) {
//        this.pics = pics;
//    }
//
//    public List<String> getHjPics() {
//        return hjPics;
//    }
//
//    public void setHjPics(List<String> hjPics) {
//        this.hjPics = hjPics;
//    }

    public String getzPics() {
        return zPics;
    }

    public void setzPics(String zPics) {
        this.zPics = zPics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressXy() {
        return addressXy;
    }

    public void setAddressXy(String addressXy) {
        this.addressXy = addressXy;
    }

    public String getBuzz() {
        return buzz;
    }

    public void setBuzz(String buzz) {
        this.buzz = buzz;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getPicWords() {
        return picWords;
    }

    public void setPicWords(String picWords) {
        this.picWords = picWords;
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

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getNeedknow() {
        return needknow;
    }

    public void setNeedknow(String needknow) {
        this.needknow = needknow;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegionid() {
        return regionid;
    }

    public void setRegionid(String regionid) {
        this.regionid = regionid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }



//    public List<String> getTjPics() {
//        return tjPics;
//    }
//
//    public void setTjPics(List<String> tjPics) {
//        this.tjPics = tjPics;
//    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getDiner() {
        return diner;
    }

    public void setDiner(String diner) {
        this.diner = diner;
    }

    public String getAddressX() {
        return addressX;
    }

    public void setAddressX(String addressX) {
        this.addressX = addressX;
    }

    public String getAddressY() {
        return addressY;
    }

    public void setAddressY(String addressY) {
        this.addressY = addressY;
    }

    public BcateInfo getBcateInfo() {
        return bcateInfo;
    }

    public void setBcateInfo(BcateInfo bcateInfo) {
        this.bcateInfo = bcateInfo;
    }
}
