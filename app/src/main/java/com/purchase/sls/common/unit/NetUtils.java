package com.purchase.sls.common.unit;


import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

public class NetUtils extends BroadcastReceiver {

    private static boolean isConnected;
    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
        initializeNetWorkState(ctx);
    }

    private static void initializeNetWorkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean curStat = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networkInfos = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo mobile = null;
            NetworkInfo wifi = null;
            NetworkInfo networkInfo1;
            for (Network network : networkInfos){
                networkInfo1 = connectivityManager.getNetworkInfo(network);
                if (networkInfo1 != null && networkInfo1.getType() == ConnectivityManager.TYPE_MOBILE )
                    mobile = networkInfo1;
                if (networkInfo1 != null && networkInfo1.getType() == ConnectivityManager.TYPE_WIFI)
                    wifi = networkInfo1;
            }
            curStat = (mobile!=null&&mobile.isConnected()) || (wifi!=null&&wifi.isConnected()) || (networkInfo != null && networkInfo.isConnectedOrConnecting());
        }else {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        curStat=true;
                    }
                }
            }
        }
//        boolean changed;
//        changed = (curStat != isConnected);
        if (curStat) {
            isConnected = true;
        } else {
            isConnected = false;
        }

//        castIfChanged(changed);
    }
    private void castIfChanged(boolean changed) {
        if (!changed)return;
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.LOLLIPOP){//5.0以下
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
    }

    public static boolean isConnected() {
        return isConnected;
    }
}
