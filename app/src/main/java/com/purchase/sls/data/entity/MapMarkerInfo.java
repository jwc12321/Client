package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/8.
 */

public class MapMarkerInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("z_pics")
    private String zPics;
    @SerializedName("buzz")
    private String buzz;
    @SerializedName("address_xy")
    private String addressXy;
    @SerializedName("rebate")
    private String rebate;
    @SerializedName("average")
    private String average;
    @SerializedName("cid")
    private String cid;
    @SerializedName("poiname")
    private String poiname;
    @SerializedName("name")
    private String name;
    @SerializedName("distance_um")
    private String distanceUm;

    public String getDistanceUm() {
        return distanceUm;
    }

    public void setDistanceUm(String distanceUm) {
        this.distanceUm = distanceUm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBuzz() {
        return buzz;
    }

    public void setBuzz(String buzz) {
        this.buzz = buzz;
    }

    public String getAddressXy() {
        return addressXy;
    }

    public void setAddressXy(String addressXy) {
        this.addressXy = addressXy;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }
}
