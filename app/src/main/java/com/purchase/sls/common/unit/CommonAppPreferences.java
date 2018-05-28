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
    public void setLocalAddress(String city,String longtitude, String latitude) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.CHOICE_CITY,city);
        editor.putString(StaticData.LONGITUDE, longtitude);
        editor.putString(StaticData.LATITUDE, latitude);
        editor.commit();
    }

    public String getCity(){
        return sharedPreferences.getString(StaticData.CHOICE_CITY,"");
    }
    //获取经度
    public String getLongitude() {
        return sharedPreferences.getString(StaticData.LONGITUDE, "");
    }

    public String getLatitude() {
        return sharedPreferences.getString(StaticData.LATITUDE, "");
    }

    public void setMianGoWhere(String mainGo){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.MAIN_GO,mainGo);
        editor.commit();
    }

    public String getMainGoWhere(){
        return sharedPreferences.getString(StaticData.MAIN_GO,"");
    }

    public void setToUpdate(String update){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.TO_UPDATE,update);
        editor.commit();
    }

    public String getToUpdate(){
        return sharedPreferences.getString(StaticData.TO_UPDATE,"");
    }

}
