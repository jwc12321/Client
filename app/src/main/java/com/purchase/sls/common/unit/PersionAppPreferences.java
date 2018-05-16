package com.purchase.sls.common.unit;

import android.content.Context;
import android.content.SharedPreferences;

import com.purchase.sls.common.StaticData;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JWC on 2018/4/25.
 */

public class PersionAppPreferences {
    SharedPreferences sharedPreferences;

    public PersionAppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(StaticData.PERSION_SPF_NAME, MODE_PRIVATE);
    }
    //个人信息
    public void setPersionInfo(String persionInfoStr) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.PERSION_INFO, persionInfoStr);
        editor.commit();
    }

    public String getPersionInfo() {
        return sharedPreferences.getString(StaticData.PERSION_INFO, "");
    }

    //上传到商城的id
    public void setShopMallId(String appuserid){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.APP_USER_ID, appuserid);
        editor.commit();
    }

    public String getShopMallId(){
        return sharedPreferences.getString(StaticData.APP_USER_ID,"");
    }

    public void clean(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
