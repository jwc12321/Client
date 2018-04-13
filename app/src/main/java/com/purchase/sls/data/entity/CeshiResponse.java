package com.purchase.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/4/13.
 */

public class CeshiResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("thirdid")
    private String thirdid;
    @SerializedName("nickname")
    private String nickname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThirdid() {
        return thirdid;
    }

    public void setThirdid(String thirdid) {
        this.thirdid = thirdid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
