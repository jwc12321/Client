package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/5/31.
 */

public class AddressInfo implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("address")
    private String address;
    @SerializedName("tel")
    private String tel;
    @SerializedName("username")
    private String username;
    @SerializedName("type")
    private String type; //0：不默认地址  1：默认地址

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
