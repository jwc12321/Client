package com.purchase.sls.push;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.purchase.sls.BuildConfig;
import com.purchase.sls.data.entity.WebViewDetailInfo;
import com.purchase.sls.webview.ui.WebViewActivity;

import java.util.List;

import javax.inject.Singleton;


/**
 * Created by Sherily on 2017/5/6.
 */
@Singleton
public class PushUtil {
    private static final Gson gson = new Gson();
    private static final String HomePaasProcessName = BuildConfig.APPLICATION_ID;

    public void parseMessage(Context context, String payloadData) {
        PushInfo pushInfo = gson.fromJson(payloadData, PushInfo.class);
        if (pushInfo.appViewId == null||pushInfo == null) return;

        if (isAppActive(context)) {
            parsePushInfo(context,pushInfo);
        } else {
            Intent lauchnIntent = context.getPackageManager().getLaunchIntentForPackage(HomePaasProcessName);
            lauchnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putSerializable("PushInfo", pushInfo);
            lauchnIntent.putExtras(bundle);
            context.startActivity(lauchnIntent);
        }
    }

    /**
     * 针对app没有启动的情况下，传递Bundle参数，解析Bundle内的信息
     * @param bundle
     */
    public void parseBundle(Context context, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        PushInfo pushInfo = (PushInfo) bundle.getSerializable("PushInfo");
        if (pushInfo != null) {
            parsePushInfo(context, pushInfo);
        }
    }

    public void parsePushInfo(Context context, PushInfo pushInfo) {
        if (pushInfo == null)return;
        String type = pushInfo.getJumpType();
        if (TextUtils.equals("1",type)){
            {
                startInternalView(context,pushInfo);
            }
        }else if (TextUtils.equals("2",type)){
            startWebView(context,pushInfo);
        }
    }

    private static void startWebView(Context context, PushInfo pushInfo) {
    }


    private static void startInternalView(Context context, PushInfo pushInfo) {
        if (pushInfo == null)return;
        int id = Integer.valueOf(pushInfo.getAppViewId());
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (id) {
        }
    }

    public static boolean isAppActive(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i);
            if (runningAppProcessInfo.processName.equals(HomePaasProcessName))
                return true;
        }
        return false;
    }
}
