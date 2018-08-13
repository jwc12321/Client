package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

public class TopCateInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("scalea")
    private String scalea;
    @SerializedName("scaleb")
    private String scaleb;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("pid")
    private String pid;
    @SerializedName("aid")
    private String aid;
    @SerializedName("pic")
    private String pic;
    @SerializedName("status")
    private String status;
    @SerializedName("rank")
    private String rank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScalea() {
        return scalea;
    }

    public void setScalea(String scalea) {
        this.scalea = scalea;
    }

    public String getScaleb() {
        return scaleb;
    }

    public void setScaleb(String scaleb) {
        this.scaleb = scaleb;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
