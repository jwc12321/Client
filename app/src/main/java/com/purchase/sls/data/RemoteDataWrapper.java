package com.purchase.sls.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/27.
 */

public class RemoteDataWrapper<T> {

    public static final String CODE_SUCCESS = "0";
    @SerializedName("code")
    public String code;
    @SerializedName("message")
    public String message;

    @Nullable
    @SerializedName("res")
    public T data;

    public String getErrorCode() {
        return code;
    }

    public String getErrorStr() {
        return code;
    }

    /**
     * 判断请求数据是否成功
     */
    public boolean isSuccess() {
        return code.equals(CODE_SUCCESS);
    }
}
