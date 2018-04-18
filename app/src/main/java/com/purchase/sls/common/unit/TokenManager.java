package com.purchase.sls.common.unit;

import android.text.TextUtils;

/**
 * Created by Administrator on 2016/3/18.
 */
public class TokenManager {

    public static final String TOKEN = "Token";

    public static String getToken(){

        return SPManager.getInstance().getData(TOKEN);
    }

    public static void saveToken(String token){
        SPManager.getInstance().putData(TOKEN,token);
    }

    public static boolean isTokenValid(){
        return !TextUtils.isEmpty(getToken());
    }

    public static void clearToken(){
       saveToken("");
    }
}
