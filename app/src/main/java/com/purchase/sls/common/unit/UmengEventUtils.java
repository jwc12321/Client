package com.purchase.sls.common.unit;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by JWC on 2018/5/25.
 */

public class UmengEventUtils {
    public static void statisticsClick(Context context,String key, String value,String type) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(key, value);
        MobclickAgent.onEvent(context, type, map);
    }

    public static void statisticsClick(Context context,String type){
        MobclickAgent.onEvent(context,type);
    }
}
