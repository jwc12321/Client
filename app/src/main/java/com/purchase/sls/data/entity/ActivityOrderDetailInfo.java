package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2018/6/8.
 */

public class ActivityOrderDetailInfo implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("orderNum")
    private String orderNum;
    @SerializedName("act_id")
    private String actId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("p_id")
    private String pId;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("address")
    private String address;
    @SerializedName("tel")
    private String tel;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("area")
    private String area;
    @SerializedName("expressNum")
    private String expressNum;
    @SerializedName("expressName")
    private String expressName;
    @SerializedName("price")
    private String price;
    @SerializedName("act_type")
    private String actType;
    @SerializedName("sendTime")
    private String sendTime;
    @SerializedName("confirmTime")
    private String confirmTime;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("goods_price")
    private String goodsPrice;
    @SerializedName("goods_logo")
    private String goodsLogo;
    @SerializedName("is_delete")
    private String isDelete;
    @SerializedName("logis")
    private List<LogisticRracesInfo> logisticRracesInfos;
    @SerializedName("connect")
    private String connect;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsLogo() {
        return goodsLogo;
    }

    public void setGoodsLogo(String goodsLogo) {
        this.goodsLogo = goodsLogo;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public List<LogisticRracesInfo> getLogisticRracesInfos() {
        return logisticRracesInfos;
    }

    public void setLogisticRracesInfos(List<LogisticRracesInfo> logisticRracesInfos) {
        this.logisticRracesInfos = logisticRracesInfos;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }
}
