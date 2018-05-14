package com.purchase.sls.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/27.
 */

public class RemoteDataWrapper<T> {

    public static final String CODE_SUCCESS = "0";
    @Nullable
    @SerializedName("errorCode")
    public String errorCode;
    @Nullable
    @SerializedName("errorStr")
    public String errorStr;
    @Nullable
    @SerializedName("resultCount")
    public String resultCount;
    @Nullable
    @SerializedName("results")
    public String results;
    @Nullable
    @SerializedName("extraInfo")
    public T data;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorStr() {
        return errorStr;
    }

    /**
     * 判断请求数据是否成功
     */
    public boolean isSuccess() {
        return errorCode.equals(CODE_SUCCESS);
    }
}
