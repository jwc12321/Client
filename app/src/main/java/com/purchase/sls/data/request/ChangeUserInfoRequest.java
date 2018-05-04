package com.purchase.sls.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/4.
 */

public class ChangeUserInfoRequest {
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("sex")
    private String sex;
    @SerializedName("birthday")
    private String birthday;

    public ChangeUserInfoRequest(String nickname, String sex, String birthday) {
        this.nickname = nickname;
        this.sex = sex;
        this.birthday = birthday;
    }
}
