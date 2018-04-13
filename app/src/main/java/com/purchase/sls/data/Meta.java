package com.purchase.sls.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/27.
 */

public class Meta {
    public static final String CODE_SUCCESS = "0";

    /**
     * 返回结果编号, 0 表示无错误, 2004 表示用户权限认证失败, 返回负数表示各种错误
     */
    @SerializedName("ErrorCode")
    private String errorCode;

    /**
     * 错误信息
     */
    @SerializedName("ErrorMsg")
    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 判断请求数据是否成功
     */
    public boolean isSuccess() {
        return errorCode.equals(CODE_SUCCESS);
    }
}
