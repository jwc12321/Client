package com.purchase.sls.wxapi;

import android.util.Log;

import com.purchase.sls.data.event.PayAbortEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * 微信分享回调必要配置.
 */
public class WXEntryActivity extends WXCallbackActivity {

    private static final String TAG = "WXEntryActivity";

    @Override
    public void onReq(BaseReq req) {
        super.onReq(req);
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onResp: 支付回调！");

        super.onResp(resp);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {//支付成功
                Log.d(TAG, "onResp: 支付成功！");
                EventBus.getDefault().post(new PayAbortEvent("支付成功",0));
                this.finish();
            } else if (resp.errCode == -2) {
                EventBus.getDefault().post(new PayAbortEvent("支付取消",-2));
                finish();
            } else {
                //未知错误，重试
            }
        }
    }
}
