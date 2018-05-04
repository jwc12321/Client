package com.purchase.sls.common.unit;

import android.content.Context;
import android.content.SharedPreferences;

import com.purchase.sls.common.StaticData;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JWC on 2018/4/25.
 */

public class CommonAppPreferences {
    SharedPreferences sharedPreferences;

    public CommonAppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(StaticData.SPF_NAME, MODE_PRIVATE);
    }

    /**
     * 当前位置
     */
    public void setLocalAddress(String longtitude, String latitude) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.LONGITUDE, longtitude);
        editor.putString(StaticData.LATITUDE, latitude);
        editor.commit();
    }

    //获取经度
    public String getLongitude() {
        return sharedPreferences.getString(StaticData.LONGITUDE, "");
    }

    public String getLatitude() {
        return sharedPreferences.getString(StaticData.LATITUDE, "");
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

    public void clean(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}
