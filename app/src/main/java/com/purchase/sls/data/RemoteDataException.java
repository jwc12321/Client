package com.purchase.sls.data;

import com.purchase.sls.common.unit.TokenManager;

/**
 * Created by Administrator on 2018/1/5.
 */

public class RemoteDataException extends Exception {
    public static final String CODE_SUCCESS = "0";

    public static final String CODE_INVALID_TOKEN = "1014";//token失效
    public static final String CODE_INVALED_SECOND_TOKEN="1015";
    public static final String CODE_INVALED_THIRD_TOKEN="1016";

    public static final String CODE_DEVICE = "2003";//非常用设备

    public static final String CODE_NO_SERVICE = "3001";//

    public static final String CODE_OUT_OF_SERVICE = "3002";//

    public static final String CODE_NOT_REGISTER = "2008";//没有注册

    public static final String CODE_NOT_REGISETR_USER = "2001";//非注册用户

    public static final String ERROR_PASSWPRD = "2002";//非注册用户

    public static final String CODE_CERTIFICATION = "2016";//注册审核中

    public static final String CODE_CERTIFICATION_FAIL= "2017";//注册审核失败

    public static final String CODE_SCRAMBLE_FAIL= "9801";//抢单失败

    private String mRetCode;

    public RemoteDataException(String retCode) {
        super();
        mRetCode = retCode;
    }

    public RemoteDataException(String retCode, String message) {
        super(message);
        mRetCode = retCode;
    }

    public String getRetCode() {
        return mRetCode;
    }

    @Override
    public String getMessage() {

        //if has custom message
        if (super.getMessage() != null) {
            return super.getMessage();
        }

        // TODO: 2017/2/27 map remote error desc
        switch (mRetCode) {
            case CODE_INVALID_TOKEN:
            case CODE_INVALED_SECOND_TOKEN:
            case CODE_INVALED_THIRD_TOKEN:
                return "token验证失败";
            default:
                return "接口操作失败!";
        }
    }

    /**
     * 判断是否token验证失败
     */
    public boolean isAuthFailed() {
        if (mRetCode.equals(CODE_INVALID_TOKEN)||mRetCode.equals(CODE_INVALED_SECOND_TOKEN)||mRetCode.equals(CODE_INVALED_THIRD_TOKEN)){
            return true;
        }
        return false;
    }

    public boolean isDeviceFailed() {
        return mRetCode.equals(CODE_DEVICE);
    }

    public boolean isNotRegister(){
        return mRetCode.equals(CODE_NOT_REGISETR_USER) || mRetCode.equals(CODE_NOT_REGISTER);
    }
    public boolean isErrorPwd(){
        return mRetCode.equals(ERROR_PASSWPRD);
    }
    public boolean isCertification(){
        return mRetCode.equals(CODE_CERTIFICATION);
    }

    public boolean isCertificationFailed() {
        return mRetCode.equals(CODE_CERTIFICATION_FAIL);
    }

    public boolean isScrambleFailed() {
        return mRetCode.equals(CODE_SCRAMBLE_FAIL);
    }
}
