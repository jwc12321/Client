package com.purchase.sls.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by JWC on 2017/5/4.
 */
public class AppRegister extends BroadcastReceiver {

	private static final String TAG = "AppRegister";
	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI iwxapi = WXAPIFactory.createWXAPI(context,null);
		iwxapi.registerApp(Constants.APP_ID);

		Log.i(TAG, "onReceive: ");
	}
}
