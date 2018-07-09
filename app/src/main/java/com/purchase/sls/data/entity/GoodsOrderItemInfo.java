package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/7/9.
 */

public class GoodsOrderItemInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("ordernum")
    private String ordernum;
    @SerializedName("oprice")
    private String oprice;
    @SerializedName("allquan_price")
    private String allquanPrice;
    @SerializedName("type")
    private String type;
    @SerializedName("paytype")
    private String paytype;
    @SerializedName("address")
    private String address;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("county")
    private String county;
    @SerializedName("name")
    private String name;
    @SerializedName("tel")
    private String tel;
    @SerializedName("goods")
    private List<GoodsInfo> goodsInfos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getOprice() {
        return oprice;
    }

    public void setOprice(String oprice) {
        this.oprice = oprice;
    }

    public String getAllquanPrice() {
        return allquanPrice;
    }

    public void setAllquanPrice(String allquanPrice) {
        this.allquanPrice = allquanPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<GoodsInfo> getGoodsInfos() {
        return goodsInfos;
    }

    public void setGoodsInfos(List<GoodsInfo> goodsInfos) {
        this.goodsInfos = goodsInfos;
    }
}
