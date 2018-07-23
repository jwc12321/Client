package com.purchase.sls.goodsordermanage.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.purchase.sls.BuildConfig;
import com.purchase.sls.common.unit.PayResult;
import com.purchase.sls.data.RxSchedulerTransformer;
import com.purchase.sls.data.entity.AliPaySignResponse;
import com.purchase.sls.data.entity.WXPaySignResponse;
import com.purchase.sls.data.remote.RestApiService;
import com.purchase.sls.data.remote.RxRemoteDataParse;
import com.purchase.sls.data.request.OrderPayRequest;
import com.purchase.sls.goodsordermanage.GoodsOrderContract;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by JWC on 2018/7/11.
 */

public class OrderPayPresenter implements GoodsOrderContract.OrderPayPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private GoodsOrderContract.OrderPayView orderPayView;
    private Context context;
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI WXAPI;

    @Inject
    public OrderPayPresenter(RestApiService restApiService, GoodsOrderContract.OrderPayView orderPayView) {
        this.restApiService = restApiService;
        this.orderPayView = orderPayView;
    }

    @Inject
    public void setupListener() {
        orderPayView.setPresenter(this);
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public void setWXAPI(IWXAPI WXAPI) {
        this.WXAPI = WXAPI;
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    /**
     * 支付宝支付
     * @param paytype
     * @param ordercode
     */
    @Override
    public void getAlipaySign(String paytype, String ordercode) {
        orderPayView.showLoading();
        OrderPayRequest orderPayRequest=new OrderPayRequest(paytype,ordercode);
        Disposable disposable=restApiService.orderAliPay(orderPayRequest)
                .flatMap(new RxRemoteDataParse<AliPaySignResponse>())
                .compose(new RxSchedulerTransformer<AliPaySignResponse>())
                .subscribe(new Consumer<AliPaySignResponse>() {
                    @Override
                    public void accept(AliPaySignResponse aliPaySignResponse) throws Exception {
                        orderPayView.dismissLoading();
                        String sign = aliPaySignResponse.getSign();
                        startAliPay(sign);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderPayView.dismissLoading();
                        orderPayView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 微信支付
     * @param paytype
     * @param ordercode
     */
    @Override
    public void getWXPaySign(String paytype, String ordercode) {
        orderPayView.showLoading();
        OrderPayRequest orderPayRequest=new OrderPayRequest(paytype,ordercode);
        Disposable disposable=restApiService.orderWXPay(orderPayRequest)
                .flatMap(new RxRemoteDataParse<WXPaySignResponse>())
                .compose(new RxSchedulerTransformer<WXPaySignResponse>())
                .subscribe(new Consumer<WXPaySignResponse>() {
                    @Override
                    public void accept(WXPaySignResponse wXPaySignResponse) throws Exception {
                        orderPayView.dismissLoading();
                        startWXPay(wXPaySignResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderPayView.dismissLoading();
                        orderPayView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    private void startAliPay(final String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask((Activity) context);
                String result = payTask.pay(sign, true);

                Message message = Message.obtain();
                message.what = SDK_PAY_FLAG;
                message.obj = result;
                handler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        orderPayView.onRechargeSuccess();
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            orderPayView.onRechargetFail();
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            orderPayView.onRechargeCancel();
                        } else {
                            orderPayView.onRechargetFail();
                        }
                    }
                    break;
            }
        }
    };

    private void startWXPay(WXPaySignResponse wxPaySignResponse){
        WXPaySignResponse.WxpaySignBean wxpaySign = wxPaySignResponse.getWxpaySign();
        if (null != wxpaySign) {
            PayReq req = new PayReq();
            req.appId = wxpaySign.getAppid();
            req.partnerId = wxpaySign.getPartnerid();
            req.prepayId = wxpaySign.getPrepayid();
            req.nonceStr = wxpaySign.getNoncestr();
            req.timeStamp = wxpaySign.getTimestamp();
            req.packageValue = wxpaySign.getPackageX();
            req.sign = wxpaySign.getSign();
            req.extData = "WxPay";
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            if (BuildConfig.DEBUG)
                Log.i(TAG, "onSucceed: " + wxpaySign.getAppid());
            orderPayView.onAppIdReceive(req.appId);
            WXAPI.sendReq(req);
        } else {
            if (BuildConfig.DEBUG)
                Log.d("PAY_GET", "返回错误");
        }
    }
}
