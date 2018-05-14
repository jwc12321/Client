package com.purchase.sls.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.purchase.sls.common.unit.CommonAppPreferences;
import com.purchase.sls.messages.ui.MessageNotificationActivity;

import javax.inject.Inject;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Sherily on 2017/5/6.
 */

public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";
    private CommonAppPreferences commonAppPreferences;
    @Inject
    PushUtil pushUtil;

    public PushReceiver() {
        DaggerPushComponent.builder().build().inject(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();//获取intent里携带的数据集合
        Log.i("极光推送:", "接收成功");
        /**
         * 一个技巧:把已知的动作写在前面,未知的动作写在后面,这样在用equals时可以避免空指针异常
         */
        //当用户点击了通知
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver]用户点击通知的标题" + bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//获取附加字段,是一个json数组
            Log.i(TAG, "[MyReceiver]附加字段" + extras);
            Intent intentActivity = new Intent(context,MessageNotificationActivity.class);
            intentActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentActivity);



            //现在说只需要去消息中心
//            if (!TextUtils.isEmpty(extras)) {
//                pushUtil.parseMessage(context, extras);
//            }
        }
        //当用户收到了通知(用户只有先收到通知,才能点击通知)
        else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);//获取通知标题
            Log.i(TAG, "[MyReceiver]用户收到了通知,通知的标题为" + title);
            String text = bundle.getString(JPushInterface.EXTRA_ALERT);//获取通知内容
            Log.i(TAG, "[MyReceiver]用户收到了通知,通知的内容为" + text);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//获取附加字段,是一个json数
        } else if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
//            SPManager.getInstance().putData("clientId",regId);
        }
    }
}
