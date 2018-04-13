package com.purchase.sls.data;

/**
 * Created by Administrator on 2018/1/5.
 */

public class RemoteDataException extends Exception {
    public static final String CODE_NOT_REGISTER = "2008";//没有注册

    public static final String CODE_NOT_REGISETR_USER = "2001";//非注册用户
    private String mRetCode;
    public RemoteDataException(String retCode, String message) {
        super(message);
        mRetCode = retCode;
    }
    public boolean isNotRegister(){
        return mRetCode.equals(CODE_NOT_REGISETR_USER) || mRetCode.equals(CODE_NOT_REGISTER);
    }
}
